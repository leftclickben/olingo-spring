package net.readify.olingo.demo.service;

import org.apache.olingo.commons.api.edm.EdmPrimitiveTypeKind;
import org.apache.olingo.commons.api.edm.FullQualifiedName;
import org.apache.olingo.commons.api.edm.provider.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DemoEdmProvider implements CsdlEdmProvider {
    static final String NAMESPACE = "OData.Demo";
    static final FullQualifiedName CONTAINER_FQN = new FullQualifiedName(NAMESPACE, "Container");
    static final FullQualifiedName PRODUCT_TYPE_FQN = new FullQualifiedName(NAMESPACE, "Product");
    static final String PRODUCTS_SET_NAME = "Products";

    @Override
    public CsdlEntityType getEntityType(FullQualifiedName entityTypeName) {
        if (!entityTypeName.equals(PRODUCT_TYPE_FQN)) {
            return null;
        }

        // create EntityType properties
        CsdlProperty id = new CsdlProperty().setName("ID").setType(EdmPrimitiveTypeKind.Int32.getFullQualifiedName());
        CsdlProperty name = new CsdlProperty().setName("Name").setType(EdmPrimitiveTypeKind.String.getFullQualifiedName());
        CsdlProperty description = new CsdlProperty().setName("Description").setType(EdmPrimitiveTypeKind.String.getFullQualifiedName());

        // create CsdlPropertyRef for Key element
        CsdlPropertyRef propertyRef = new CsdlPropertyRef();
        propertyRef.setName("ID");

        // configure EntityType
        CsdlEntityType entityType = new CsdlEntityType();
        entityType.setName(PRODUCT_TYPE_FQN.getName());
        entityType.setProperties(Arrays.asList(id, name, description));
        entityType.setKey(Collections.singletonList(propertyRef));

        return entityType;
    }

    @Override
    public CsdlEntitySet getEntitySet(FullQualifiedName entityContainer, String entitySetName) {
        if (!entityContainer.equals(CONTAINER_FQN) || !entitySetName.equals(PRODUCTS_SET_NAME)) {
            return null;
        }

        CsdlEntitySet entitySet = new CsdlEntitySet();
        entitySet.setName(PRODUCTS_SET_NAME);
        entitySet.setType(PRODUCT_TYPE_FQN);
        return entitySet;
    }

    @Override
    public CsdlEntityContainerInfo getEntityContainerInfo(FullQualifiedName entityContainerName) {
        // This method is invoked when displaying the Service Document at e.g. http://localhost:8080/DemoService/DemoService.svc
        if (entityContainerName != null && !entityContainerName.equals(CONTAINER_FQN)) {
            return null;
        }

        CsdlEntityContainerInfo entityContainerInfo = new CsdlEntityContainerInfo();
        entityContainerInfo.setContainerName(CONTAINER_FQN);
        return entityContainerInfo;
    }

    @Override
    public CsdlEntityContainer getEntityContainer() {
        // create EntitySets
        List<CsdlEntitySet> entitySets = new ArrayList<>();
        entitySets.add(getEntitySet(CONTAINER_FQN, PRODUCTS_SET_NAME));

        // create EntityContainer
        CsdlEntityContainer entityContainer = new CsdlEntityContainer();
        entityContainer.setName(CONTAINER_FQN.getName());
        entityContainer.setEntitySets(entitySets);

        return entityContainer;
    }

    @Override
    public List<CsdlSchema> getSchemas() {
        // create Schema
        CsdlSchema schema = new CsdlSchema();
        schema.setNamespace(NAMESPACE);

        // add EntityTypes
        List<CsdlEntityType> entityTypes = new ArrayList<>();
        entityTypes.add(getEntityType(PRODUCT_TYPE_FQN));
        schema.setEntityTypes(entityTypes);

        // add EntityContainer
        schema.setEntityContainer(getEntityContainer());

        // finally
        List<CsdlSchema> schemas = new ArrayList<>();
        schemas.add(schema);

        return schemas;
    }

    @Override
    public CsdlAnnotations getAnnotationsGroup(FullQualifiedName targetName, String qualifier) {
        return null;
    }

    @Override
    public CsdlEnumType getEnumType(FullQualifiedName fullQualifiedName) {
        return null;
    }

    @Override
    public CsdlTypeDefinition getTypeDefinition(FullQualifiedName fullQualifiedName) {
        return null;
    }

    @Override
    public CsdlComplexType getComplexType(FullQualifiedName fullQualifiedName) {
        return null;
    }

    @Override
    public List<CsdlAction> getActions(FullQualifiedName fullQualifiedName) {
        return null;
    }

    @Override
    public List<CsdlFunction> getFunctions(FullQualifiedName fullQualifiedName) {
        return null;
    }

    @Override
    public CsdlTerm getTerm(FullQualifiedName fullQualifiedName) {
        return null;
    }

    @Override
    public CsdlSingleton getSingleton(FullQualifiedName fullQualifiedName, String s) {
        return null;
    }

    @Override
    public CsdlActionImport getActionImport(FullQualifiedName fullQualifiedName, String s) {
        return null;
    }

    @Override
    public CsdlFunctionImport getFunctionImport(FullQualifiedName fullQualifiedName, String s) {
        return null;
    }

    @Override
    public List<CsdlAliasInfo> getAliasInfos() {
        return null;
    }
}
