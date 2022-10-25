package com.heartfulness.platform.grpc.seeker.service;

import com.google.protobuf.Empty;
import com.google.protobuf.Int64Value;
import com.heartfulness.platform.grpc.seeker.service.Seeker;
import com.heartfulness.platform.grpc.seeker.data.SeekerEntity;
import com.heartfulness.platform.grpc.seeker.data.SeekerServiceDataRepository;
import io.grpc.Metadata;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;

import java.util.List;
import java.util.stream.Collectors;

public class SeekerServiceGrpcImpl extends SeekerServiceGrpc.SeekerServiceImplBase {

    com.heartfulness.platform.grpc.seeker.data.SeekerServiceDataRepository seekerServiceDataRepository = new SeekerServiceDataRepository();

    /**
     * @param request
     * @param responseObserver
     */
    @Override
    public void createSeeker(Seeker request, StreamObserver<Seeker> responseObserver) {
        SeekerEntity entry = SeekerEntity.fromProto(request);
        entry = seekerServiceDataRepository.createSeeker(entry);
        responseObserver.onNext(entry.toProto());
        responseObserver.onCompleted();
    }

    /**
     * @param request
     * @param responseObserver
     */
    @Override
    public void updateSeeker(Seeker request, StreamObserver<Seeker> responseObserver) {
        SeekerEntity entry = SeekerEntity.fromProto(request);
        entry = seekerServiceDataRepository.updateSeeker(entry);
        responseObserver.onNext(entry.toProto());
        responseObserver.onCompleted();
    }

    /**
     * @param filter
     * @param responseObserver
     */
    @Override
    public void findSeekerByFilter(SeekerFilter filter, StreamObserver<SeekerList> responseObserver) {
        List<SeekerEntity> seekerEntity = seekerServiceDataRepository.findSeekersByFilter(filter);
        List<Seeker> seekers = seekerEntity.stream().map(SeekerEntity::toProto).collect(Collectors.toList());
        SeekerList seekerList = SeekerList.newBuilder().addAllResultList(seekers)
                .setResultCount(Int64Value.newBuilder().setValue(seekerEntity.size()).build()).build();
        responseObserver.onNext(seekerList);
        responseObserver.onCompleted();
    }

    /**
     * @param request
     * @param responseObserver
     */
    @Override
    public void deleteSeeker(Int64Value request, StreamObserver<Empty> responseObserver) {
        seekerServiceDataRepository.deleteSeeker(request.getValue());
        responseObserver.onNext(Empty.getDefaultInstance());
        responseObserver.onCompleted();
    }

    /**
     * @param id
     * @param responseObserver
     */
    @Override
    public void findSeekerById(Int64Value id, StreamObserver<Seeker> responseObserver) {
        SeekerEntity seekerEntity = seekerServiceDataRepository.findSeekerById(id.getValue());
        if (seekerEntity == null) {
            Metadata metadata = new Metadata();
            responseObserver.onError(
                    Status.UNAVAILABLE.withDescription("Seeker not found !")
                            .asRuntimeException(metadata));
        } else {
            responseObserver.onNext(seekerEntity.toProto());
            responseObserver.onCompleted();
        }

    }

    /**
     * @param request
     * @param responseObserver
     */
    @Override
    public void findAllSeekers(Empty request, StreamObserver<Seeker> responseObserver) {
        List<SeekerEntity> seekerEntities = seekerServiceDataRepository.findAllSeekers();
        for (SeekerEntity seeker : seekerEntities) {
            responseObserver.onNext(seeker.toProto());
        }
        responseObserver.onCompleted();
    }

    /**
     * @param responseObserver
     */
    @Override
    public StreamObserver<Seeker> updateSeekersInBatch(StreamObserver<Seeker> responseObserver) {
        return new StreamObserver<Seeker>() {
            @Override
            public void onNext(Seeker request) {

                SeekerEntity savedSeeker = seekerServiceDataRepository.updateSeeker( SeekerEntity.fromProto(request));
                responseObserver.onNext(savedSeeker.toProto());
            }

            @Override
            public void onError(Throwable t) {
                t.printStackTrace();
            }

            @Override
            public void onCompleted() {
                responseObserver.onCompleted();
            }
        };
    }
}
