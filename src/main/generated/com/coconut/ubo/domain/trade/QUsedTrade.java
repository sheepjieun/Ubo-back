package com.coconut.ubo.domain.trade;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUsedTrade is a Querydsl query type for UsedTrade
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUsedTrade extends EntityPathBase<UsedTrade> {

    private static final long serialVersionUID = -632708955L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUsedTrade usedTrade = new QUsedTrade("usedTrade");

    public final QTradeHistory _super;

    // inherited
    public final com.coconut.ubo.domain.user.QUser buyer;

    //inherited
    public final NumberPath<Long> id;

    public final com.coconut.ubo.domain.item.QItem item;

    // inherited
    public final com.coconut.ubo.domain.user.QUser seller;

    public final DateTimePath<java.time.LocalDateTime> tradeDate = createDateTime("tradeDate", java.time.LocalDateTime.class);

    public QUsedTrade(String variable) {
        this(UsedTrade.class, forVariable(variable), INITS);
    }

    public QUsedTrade(Path<? extends UsedTrade> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUsedTrade(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUsedTrade(PathMetadata metadata, PathInits inits) {
        this(UsedTrade.class, metadata, inits);
    }

    public QUsedTrade(Class<? extends UsedTrade> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new QTradeHistory(type, metadata, inits);
        this.buyer = _super.buyer;
        this.id = _super.id;
        this.item = inits.isInitialized("item") ? new com.coconut.ubo.domain.item.QItem(forProperty("item"), inits.get("item")) : null;
        this.seller = _super.seller;
    }

}

