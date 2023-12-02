package com.coconut.ubo.repository.user;

import com.coconut.ubo.domain.trade.TradeHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TradeRepository extends JpaRepository<TradeHistory, Long> {
}
