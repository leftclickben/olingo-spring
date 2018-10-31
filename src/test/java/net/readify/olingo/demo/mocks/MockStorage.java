package net.readify.olingo.demo.mocks;

import net.readify.olingo.demo.data.Storage;
import net.readify.olingo.demo.service.DemoEdmProvider;
import net.readify.olingo.demo.util.Util;
import org.apache.olingo.commons.api.data.Entity;
import org.apache.olingo.commons.api.data.EntityCollection;
import org.apache.olingo.commons.api.data.Property;
import org.apache.olingo.commons.api.data.ValueType;
import org.apache.olingo.commons.api.edm.EdmEntitySet;
import org.apache.olingo.commons.api.edm.EdmEntityType;
import org.apache.olingo.commons.api.ex.ODataRuntimeException;
import org.apache.olingo.commons.api.http.HttpStatusCode;
import org.apache.olingo.server.api.ODataApplicationException;
import org.apache.olingo.server.api.uri.UriParameter;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MockStorage implements Storage {

    private List<Entity> productList;

    public MockStorage() {
        productList = new ArrayList<>();
        initSampleData();
    }

    @Override
    public EntityCollection readEntitySetData(EdmEntitySet edmEntitySet) {
        if (edmEntitySet.getName().equals(DemoEdmProvider.PRODUCTS_SET_NAME)) {
            return getProducts();
        }
        return null;
    }

    @Override
    public Entity readEntityData(EdmEntitySet edmEntitySet, List<UriParameter> keyParams) throws ODataApplicationException {
        EdmEntityType edmEntityType = edmEntitySet.getEntityType();
        if (edmEntityType.getName().equals(DemoEdmProvider.PRODUCT_TYPE_FQN.getName())) {
            return getProduct(edmEntityType, keyParams);
        }
        return null;
    }

    private EntityCollection getProducts() {
        EntityCollection entitySet = new EntityCollection();
        for (Entity productEntity : productList) {
            entitySet.getEntities().add(productEntity);
        }
        return entitySet;
    }


    private Entity getProduct(EdmEntityType edmEntityType, List<UriParameter> keyParams) throws ODataApplicationException {
        EntityCollection entitySet = getProducts();
        Entity requestedEntity = Util.findEntity(edmEntityType, entitySet, keyParams);
        if (requestedEntity == null) {
            throw new ODataApplicationException("Entity for requested key doesn't exist", HttpStatusCode.NOT_FOUND.getStatusCode(), Locale.ENGLISH);
        }
        return requestedEntity;
    }

    private void initSampleData() {
        final Entity e1 = new Entity()
            .addProperty(new Property(null, "ID", ValueType.PRIMITIVE, 1))
            .addProperty(new Property(null, "Name", ValueType.PRIMITIVE, "Xyzzy"))
            .addProperty(new Property(null, "Description", ValueType.PRIMITIVE,
                "Takes you back to the twisty maze of endless passages"));
        e1.setId(createId("Products", 1));
        productList.add(e1);

        final Entity e2 = new Entity()
            .addProperty(new Property(null, "ID", ValueType.PRIMITIVE, 2))
            .addProperty(new Property(null, "Name", ValueType.PRIMITIVE, "TTTTTTTTTTT"))
            .addProperty(new Property(null, "Description", ValueType.PRIMITIVE,
                "Switches off computer assist"));
        e2.setId(createId("Products", 2));
        productList.add(e2);

        final Entity e3 = new Entity()
            .addProperty(new Property(null, "ID", ValueType.PRIMITIVE, 3))
            .addProperty(new Property(null, "Name", ValueType.PRIMITIVE, "IDDQD"))
            .addProperty(new Property(null, "Description", ValueType.PRIMITIVE,
                "GOD MODE!"));
        e3.setId(createId("Products", 3));
        productList.add(e3);
    }

    private URI createId(String entitySetName, Object id) {
        try {
            return new URI(entitySetName + "(" + String.valueOf(id) + ")");
        } catch (URISyntaxException e) {
            throw new ODataRuntimeException("Unable to create id for entity: " + entitySetName, e);
        }
    }
}
