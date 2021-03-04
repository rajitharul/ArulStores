package com.luv2code.ecommerce.config;

import com.luv2code.ecommerce.entity.Country;
import com.luv2code.ecommerce.entity.Product;
import com.luv2code.ecommerce.entity.ProductCategory;
import com.luv2code.ecommerce.entity.State;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;

import javax.persistence.EntityManager;
import javax.persistence.metamodel.EntityType;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Configuration
public class MyDataRestConfig implements RepositoryRestConfigurer {

    private EntityManager entityManager;

    @Autowired
    public MyDataRestConfig(EntityManager theentityManager){

        entityManager = theentityManager;

    }


    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {

        HttpMethod []  theUnsuppportedactions = {HttpMethod.PUT ,HttpMethod.POST , HttpMethod.DELETE };

        //disable HTTP methods for productd : PUT, POST , DELETE
        disableHttpMethods(Product.class , config, theUnsuppportedactions);
        //disable HTTP methods for Product category  : PUT, POST , DELETE
        disableHttpMethods(ProductCategory.class , config, theUnsuppportedactions);
        //disable HTTP methods for Country  : PUT, POST , DELETE
        disableHttpMethods(Country.class , config, theUnsuppportedactions);
        //disable HTTP methods for State  : PUT, POST , DELETE
        disableHttpMethods(State.class , config, theUnsuppportedactions);


        //call an internal helper method to expose the ID;s
        exposeIds(config);



    }

    private void disableHttpMethods(Class theClass , RepositoryRestConfiguration config, HttpMethod[] theUnsuppportedactions) {
        config.getExposureConfiguration().
                forDomainType(theClass)
                .withItemExposure((metdata, httpMethods) -> httpMethods.disable(theUnsuppportedactions))
                .withCollectionExposure(((metdata, httpMethods) -> httpMethods.disable(theUnsuppportedactions)));
    }

    private void exposeIds(RepositoryRestConfiguration config) {
        //expose entity ids

        //get a list fo all entity classes from the entity manager;

        Set<EntityType<?>> entities = entityManager.getMetamodel().getEntities();

        //create an array of entity types
        List<Class> entityClasses = new ArrayList<>();

        //get the entitiy typer for the entities

        for(EntityType tempEntityType : entities){

            entityClasses.add(tempEntityType.getJavaType());

        }

        // -expose the entity ids for the

        Class [] domainTypes = entityClasses.toArray(new Class[0]);
        config.exposeIdsFor(domainTypes);


    }
}
