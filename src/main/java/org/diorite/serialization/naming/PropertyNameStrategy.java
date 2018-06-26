package org.diorite.serialization.naming;

public interface PropertyNameStrategy
{
    PropertyNamingStrategy getNamingStrategy();

    PropertyResolutionStrategy getResolutionStrategy();
}
