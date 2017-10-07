package com.pegasus.condominio.tenant;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.stereotype.Component;

@Component
public class TenantIdentifierResolver implements CurrentTenantIdentifierResolver {

    @Override
    public String resolveCurrentTenantIdentifier() {
        String tenantId = TenantContext.getCurrentTenant();
        if (tenantId != null) {
            System.out.println("Resolver:"+tenantId);
            return tenantId;

        }
        System.out.println("Resolver Default:"+TenantContext.DEFAULT_TENANT);
        return TenantContext.DEFAULT_TENANT;
    }

    @Override
    public boolean validateExistingCurrentSessions() {
        return true;
    }
}


