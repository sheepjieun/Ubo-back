package com.coconut.ubo.domain.item;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUsedItem is a Querydsl query type for UsedItem
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUsedItem extends EntityPathBase<UsedItem> {

    private static final long serialVersionUID = -794213325L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUsedItem usedItem = new QUsedItem("usedItem");

    public final QItem _super;

    public final EnumPath<Category> category = createEnum("category", Category.class);

    public final EnumPath<Trace> coverCondition = createEnum("coverCondition", Trace.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createAt;

    //inherited
    public final StringPath description;

    //inherited
    public final NumberPath<Long> id;

    //inherited
    public final EnumPath<ItemStatus> itemStatus;

    //inherited
    public final NumberPath<Integer> likeCount;

    //inherited
    public final StringPath major;

    public final EnumPath<Trace> nameWritten = createEnum("nameWritten", Trace.class);

    public final EnumPath<Trace> pageDamage = createEnum("pageDamage", Trace.class);

    public final EnumPath<Trace> pageDiscoloration = createEnum("pageDiscoloration", Trace.class);

    //inherited
    public final NumberPath<Integer> price;

    // inherited
    public final com.coconut.ubo.domain.user.QUser seller;

    //inherited
    public final StringPath title;

    public final EnumPath<Trace> underlineTrace = createEnum("underlineTrace", Trace.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateAt;

    //inherited
    public final NumberPath<Integer> viewCount;

    public final EnumPath<Trace> writingTrace = createEnum("writingTrace", Trace.class);

    public QUsedItem(String variable) {
        this(UsedItem.class, forVariable(variable), INITS);
    }

    public QUsedItem(Path<? extends UsedItem> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUsedItem(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUsedItem(PathMetadata metadata, PathInits inits) {
        this(UsedItem.class, metadata, inits);
    }

    public QUsedItem(Class<? extends UsedItem> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new QItem(type, metadata, inits);
        this.createAt = _super.createAt;
        this.description = _super.description;
        this.id = _super.id;
        this.itemStatus = _super.itemStatus;
        this.likeCount = _super.likeCount;
        this.major = _super.major;
        this.price = _super.price;
        this.seller = _super.seller;
        this.title = _super.title;
        this.updateAt = _super.updateAt;
        this.viewCount = _super.viewCount;
    }

}

