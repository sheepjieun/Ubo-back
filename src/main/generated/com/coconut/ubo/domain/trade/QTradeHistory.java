package com.coconut.ubo.domain.trade;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTradeHistory is a Querydsl query type for TradeHistory
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTradeHistory extends EntityPathBase<TradeHistory> {

    private static final long serialVersionUID = 923938002L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTradeHistory tradeHistory = new QTradeHistory("tradeHistory");

    public final com.coconut.ubo.domain.user.QUser buyer;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.coconut.ubo.domain.user.QUser seller;

    public QTradeHistory(String variable) {
        this(TradeHistory.class, forVariable(variable), INITS);
    }

    public QTradeHistory(Path<? extends TradeHistory> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTradeHistory(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTradeHistory(PathMetadata metadata, PathInits inits) {
        this(TradeHistory.class, metadata, inits);
    }

    public QTradeHistory(Class<? extends TradeHistory> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.buyer = inits.isInitialized("buyer") ? new com.coconut.ubo.domain.user.QUser(forProperty("buyer"), inits.get("buyer")) : null;
        this.seller = inits.isInitialized("seller") ? new com.coconut.ubo.domain.user.QUser(forProperty("seller"), inits.get("seller")) : null;
    }

}

