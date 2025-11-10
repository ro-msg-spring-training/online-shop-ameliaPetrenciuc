package ro.msg.learning.shop.service.strategy;

import ro.msg.learning.shop.entity.Product;

import java.util.List;
import java.util.Map;

public class FallbackLocationStrategy implements LocationStrategy{
    private final List<LocationStrategy> locationStrategyList;

    public FallbackLocationStrategy(List<LocationStrategy> locationStrategyList) {
        this.locationStrategyList = locationStrategyList;
    }

    @Override
    public List<LocationProductQuantity> findLocations(Map<Product, Integer> products) {
        for(LocationStrategy locationStrategy:locationStrategyList){
            try{
                List<LocationProductQuantity> res=locationStrategy.findLocations(products);
                if(!res.isEmpty()){
                    return res;
                }
            }catch(RuntimeException e){
                System.out.println("Strategy"+ locationStrategy.getClass().getSimpleName()+" failed: "+e.getMessage());
            }
        }
        throw new RuntimeException("All strategies  failed");
    }
}
