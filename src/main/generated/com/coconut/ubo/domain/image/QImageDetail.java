package com.coconut.ubo.domain.image;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QImageDetail is a Querydsl query type for ImageDetail
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QImageDetail extends EntityPathBase<ImageDetail> {

    private static final long serialVersionUID = -1677528959L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QImageDetail imageDetail = new QImageDetail("imageDetail");

    public final StringPath fileName = createString("fileName");

    public final NumberPath<Integer> fileOrder = createNumber("fileOrder", Integer.class);

    public final StringPath fileUrl = createString("fileUrl");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QImageSet imageSet;

    public QImageDetail(String variable) {
        this(ImageDetail.class, forVariable(variable), INITS);
    }

    public QImageDetail(Path<? extends ImageDetail> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QImageDetail(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QImageDetail(PathMetadata metadata, PathInits inits) {
        this(ImageDetail.class, metadata, inits);
    }

    public QImageDetail(Class<? extends ImageDetail> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.imageSet = inits.isInitialized("imageSet") ? new QImageSet(forProperty("imageSet"), inits.get("imageSet")) : null;
    }

}

