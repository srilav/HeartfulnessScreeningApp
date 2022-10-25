package com.heartfulness.platform.grpc.seeker.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.protobuf.Empty;
import com.google.protobuf.Int64Value;
import com.heartfulness.platform.grpc.seeker.data.SeekerEntity;
import com.heartfulness.platform.grpc.seeker.service.Seeker;
import com.heartfulness.platform.grpc.seeker.service.SeekerFilter;
import com.heartfulness.platform.grpc.seeker.service.SeekerList;
import com.heartfulness.platform.grpc.seeker.service.SeekerServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;

import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Grpc Client
 */
public class GrpcClient {

    private ManagedChannel channel;
    private SeekerServiceGrpc.SeekerServiceBlockingStub seekerServiceBlockingStub;
    private SeekerServiceGrpc.SeekerServiceStub seekerServiceAsyncStub;

    public GrpcClient() {
        initializeStub();
    }

    public static void main(String[] args) throws Exception {

        new GrpcClient().exercieAllAPIs();
    }

    private static void log(String message) {
        System.out.println(message);
    }

     private void exercieAllAPIs() throws InterruptedException {
        GrpcClient client = new GrpcClient();

        try {
              log("gRPC Client");
            Seeker seeker = client.findSeekerById(1000);
            log(seeker.toString());

            CountDownLatch finishLatch = new CountDownLatch(1);
            client.updateSeekersUsingStream(finishLatch);
            //client.fetchAllSeekersUsingStream();
            client.updateSeeker(1000);
//            Seeker seeker = client.findSeekerById(1000);
            SeekerFilter filter = SeekerFilter.newBuilder().build();
            List<Seeker> seekersList = client.findSeekerByFilter(filter);
            client.deleteSeeker(1000);
            seeker = client.findSeekerById(1000);

            if (!finishLatch.await(10, TimeUnit.SECONDS)) {
                log("gRPC API call can not finish within 10 seconds");
            }

        } catch (StatusRuntimeException e) {
            // Do not use Status.equals(...) - it's not well defined. Compare Code directly.
            if (e.getStatus().getCode() == Status.Code.DEADLINE_EXCEEDED) {
                log(e.getMessage());
            }
        } finally {

            client.shutdown();
        }
    }


    private void initializeStub() {
        channel = ManagedChannelBuilder.forAddress("localhost", 8080).usePlaintext().build();
        seekerServiceBlockingStub = SeekerServiceGrpc.newBlockingStub(channel);
        seekerServiceAsyncStub = SeekerServiceGrpc.newStub(channel);
    }

    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    private Seeker findSeekerById(int id) {
        return seekerServiceBlockingStub.findSeekerById(Int64Value.of(id));
    }

    public Seeker updateSeeker(int id) {
        Seeker seeker = Seeker.newBuilder().setId(id)
                .setSeekerName("Administration")
                .setLocation("Foster City")
                .build();
        seekerServiceBlockingStub.updateSeeker(seeker);
        return seeker;
    }

    public List<Seeker> findSeekerByFilter(SeekerFilter filter) {
        SeekerList seekerList = seekerServiceBlockingStub.findSeekerByFilter(filter);
        return seekerList.getResultListList();
    }

    public void fetchAllSeekersUsingStream(final CountDownLatch finishLatch) throws InterruptedException {
        seekerServiceAsyncStub.findAllSeekers(Empty.getDefaultInstance(), new StreamObserver<Seeker>() {
            public void onNext(Seeker seeker) {
                log("fetchAllSeekers:: Seeker ~ " + seeker);
                finishLatch.countDown();
            }

            public void onError(Throwable throwable) {
                finishLatch.countDown();
            }

            public void onCompleted() {
                finishLatch.countDown();
            }
        });

    }

    public void deleteSeeker(int id) {
        seekerServiceBlockingStub.deleteSeeker(Int64Value.of(id));
    }

    private List<SeekerEntity> readDataFromFileStore() {
        List<SeekerEntity> deptsList = null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            InputStream jsonInput = this.getClass().getResourceAsStream("/test-data.json");
            deptsList = mapper.readValue(jsonInput,
                    new TypeReference<List<SeekerEntity>>() {
                    });
        } catch (Exception ex) {
            ex.printStackTrace();
            deptsList = Collections.emptyList();
        }

        return deptsList;
    }

    private void updateSeekersUsingStream(final CountDownLatch finishLatch) throws InterruptedException {
        List<SeekerEntity> seekersList = readDataFromFileStore();

        StreamObserver<Seeker> requestObserver = seekerServiceAsyncStub.updateSeekersInBatch(new StreamObserver<Seeker>() {
            public void onNext(Seeker seeker) {
                log("updateSeekersInBatch:: Seeker ~ " + seeker);
            }

            public void onError(Throwable th) {
                th.printStackTrace();
            }

            public void onCompleted() {
                log("Completed!");
                finishLatch.countDown();
            }
        });

        try {
            for (SeekerEntity seeker : seekersList) {
                requestObserver.onNext(seeker.toProto());
            }
        } catch (Exception ex) {
            requestObserver.onError(ex);
        }
        requestObserver.onCompleted();

    }
}
