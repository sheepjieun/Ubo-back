package com.coconut.ubo.domain.image;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QImageSet is a Querydsl query type for ImageSet
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QImageSet extends EntityPathBase<ImageSet> {

    private static final long serialVersionUID = -1595715022L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QImageSet imageSet = new QImageSet("imageSet");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<ImageDetail, QImageDetail> imageDetails = this.<ImageDetail, QImageDetail>createList("imageDetails", ImageDetail.class, QImageDetail.class, PathInits.DIRECT2);

    public final com.coconut.ubo.domain.item.QItem item;

    public QImageSet(String variable) {
        this(ImageSet.class, forVariable(variable), INITS);
    }

    public QImageSet(Path<? extends ImageSet> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QImageSet(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QImageSet(PathMetadata metadata, PathInits inits) {
        this(ImageSet.class, metadata, inits);
    }

    public QImageSet(Class<? extends ImageSet> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.item = inits.isInitialized("item") ? new com.coconut.ubo.domain.item.QItem(forProperty("item"), inits.get("item")) : null;
    }

}

