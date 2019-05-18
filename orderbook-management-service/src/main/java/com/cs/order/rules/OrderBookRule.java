package com.cs.order.rules;

import com.cs.core.ExecutionContext;

public interface OrderBookRule {

	public void execute(ExecutionContext executionContext);
}
