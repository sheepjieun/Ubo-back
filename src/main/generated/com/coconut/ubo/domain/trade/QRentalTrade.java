package com.coconut.ubo.domain.trade;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRentalTrade is a Querydsl query type for RentalTrade
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRentalTrade extends EntityPathBase<RentalTrade> {

    private static final long serialVersionUID = -1393586146L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRentalTrade rentalTrade = new QRentalTrade("rentalTrade");

    public final QTradeHistory _super;

    // inherited
    public final com.coconut.ubo.domain.user.QUser buyer;

    //inherited
    public final NumberPath<Long> id;

    public final com.coconut.ubo.domain.item.QItem item;

    public final DateTimePath<java.time.LocalDateTime> rentalEndDate = createDateTime("rentalEndDate", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> rentalStartDate = createDateTime("rentalStartDate", java.time.LocalDateTime.class);

    // inherited
    public final com.coconut.ubo.domain.user.QUser seller;

    public QRentalTrade(String variable) {
        this(RentalTrade.class, forVariable(variable), INITS);
    }

    public QRentalTrade(Path<? extends RentalTrade> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRentalTrade(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRentalTrade(PathMetadata metadata, PathInits inits) {
        this(RentalTrade.class, metadata, inits);
    }

    public QRentalTrade(Class<? extends RentalTrade> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new QTradeHistory(type, metadata, inits);
        this.buyer = _super.buyer;
        this.id = _super.id;
        this.item = inits.isInitialized("item") ? new com.coconut.ubo.domain.item.QItem(forProperty("item"), inits.get("item")) : null;
        this.seller = _super.seller;
    }

}

