package com.example.multitenantusers.tenant;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class MultitenantDataSource extends AbstractRoutingDataSource {


    @Override
    protected Object determineCurrentLookupKey() {

        System.err.println("Current lookup key " + TenantContext.getCurrentTenant());
        return TenantContext.getCurrentTenant();
    }
}