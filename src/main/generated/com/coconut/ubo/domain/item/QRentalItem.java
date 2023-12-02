package com.coconut.ubo.domain.item;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRentalItem is a Querydsl query type for RentalItem
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRentalItem extends EntityPathBase<RentalItem> {

    private static final long serialVersionUID = 1888740698L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRentalItem rentalItem = new QRentalItem("rentalItem");

    public final QItem _super;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createAt;

    public final NumberPath<Integer> deposit = createNumber("deposit", Integer.class);

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

    //inherited
    public final NumberPath<Integer> price;

    // inherited
    public final com.coconut.ubo.domain.user.QUser seller;

    //inherited
    public final StringPath title;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateAt;

    //inherited
    public final NumberPath<Integer> viewCount;

    public QRentalItem(String variable) {
        this(RentalItem.class, forVariable(variable), INITS);
    }

    public QRentalItem(Path<? extends RentalItem> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRentalItem(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRentalItem(PathMetadata metadata, PathInits inits) {
        this(RentalItem.class, metadata, inits);
    }

    public QRentalItem(Class<? extends RentalItem> type, PathMetadata metadata, PathInits inits) {
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

