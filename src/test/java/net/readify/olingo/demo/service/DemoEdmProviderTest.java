package net.readify.olingo.demo.service;

import org.apache.olingo.commons.api.edm.EdmPrimitiveTypeKind;
import org.apache.olingo.commons.api.edm.provider.*;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class DemoEdmProviderTest {
    private DemoEdmProvider provider;

    @Before
    public void instantiateProvider() {
        provider = new DemoEdmProvider();
    }

    @Test
    public void testProductEntityTypeDefinition() {
        final CsdlEntityType entityType = provider.getEntityType(DemoEdmProvider.PRODUCT_TYPE_FQN);
        assertNotNull(entityType);

        assertEquals(DemoEdmProvider.PRODUCT_TYPE_FQN.getName(), entityType.getName());
        assertEquals(3, entityType.getProperties().size());

        assertEquals("ID", entityType.getProperties().get(0).getName());
        assertEquals(EdmPrimitiveTypeKind.Int32.getFullQualifiedName(), entityType.getProperties().get(0).getTypeAsFQNObject());

        assertEquals("Name", entityType.getProperties().get(1).getName());
        assertEquals(EdmPrimitiveTypeKind.String.getFullQualifiedName(), entityType.getProperties().get(1).getTypeAsFQNObject());

        assertEquals("Description", entityType.getProperties().get(2).getName());
        assertEquals(EdmPrimitiveTypeKind.String.getFullQualifiedName(), entityType.getProperties().get(2).getTypeAsFQNObject());

        assertEquals(1, entityType.getKey().size());
        assertEquals("ID", entityType.getKey().get(0).getName());
    }

    @Test
    public void testEntitySetDefinition() {
        final CsdlEntitySet entitySet = provider.getEntitySet(DemoEdmProvider.CONTAINER_FQN, DemoEdmProvider.PRODUCTS_SET_NAME);
        assertNotNull(entitySet);
        assertEquals(DemoEdmProvider.PRODUCTS_SET_NAME, entitySet.getName());
        assertEquals(DemoEdmProvider.PRODUCT_TYPE_FQN, entitySet.getTypeFQN());
    }

    @Test
    public void testEntityContainerInfo() {
        final CsdlEntityContainerInfo info = provider.getEntityContainerInfo(DemoEdmProvider.CONTAINER_FQN);
        assertNotNull(info);
        assertEquals(DemoEdmProvider.CONTAINER_FQN, info.getContainerName());
    }

    @Test
    public void testEntityContainerDefinition() {
        final CsdlEntityContainer container = provider.getEntityContainer();
        assertNotNull(container);
        assertEquals(DemoEdmProvider.CONTAINER_FQN.getName(), container.getName());
        assertEquals(1, container.getEntitySets().size());
        assertEquals(DemoEdmProvider.PRODUCTS_SET_NAME, container.getEntitySets().get(0).getName());
    }

    @Test
    public void testSchemaDefinitions() {
        final List<CsdlSchema> schemas = provider.getSchemas();
        assertNotNull(schemas);
        assertEquals(1, schemas.size());
        assertEquals(1, schemas.get(0).getEntityTypes().size());
        assertEquals(DemoEdmProvider.NAMESPACE, schemas.get(0).getNamespace());
        assertEquals(DemoEdmProvider.PRODUCT_TYPE_FQN.getName(), schemas.get(0).getEntityTypes().get(0).getName());
        assertEquals(DemoEdmProvider.CONTAINER_FQN.getName(), schemas.get(0).getEntityContainer().getName());
    }
}
