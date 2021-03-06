package controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.EbeanServer;
import com.google.inject.Inject;

import models.District;
import models.PlaceValues;
import models.Places;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

public class Test extends Controller{
	private final EbeanServer db;
	
	 	@Inject
	    public Test()
	    {
	        db = Ebean.getServer("default");
	    }

	 	public Result getAllDistricts() {
	        Map<String, Object> hashMap = new HashMap<>();
	        List<District> distList = db.find(District.class).findList();
	        hashMap.put("District", distList);
	        return ok(Json.toJson(hashMap));
	    }
	 	
	 	public Result getAllDistrictsOrder() {
	        Map<String, Object> hashMap = new HashMap<>();
	        List<District> distList = db.find(District.class).findList();
	        Collections.sort(distList, new DistrictComparator() );
	        hashMap.put("District", distList);
	        return ok(Json.toJson(hashMap));
	    }
	 	
	 	public Result getAllPlaces() {
	        Map<String, Object> hashMap = new HashMap<>();
	        List<Places> placeList = db.find(Places.class).findList();
	        hashMap.put("Places", placeList);
	        return ok(Json.toJson(hashMap));
	 	}
	 	
	 	public Result getAllPlacesOrder() {
	 		PlaceComparator pc= new PlaceComparator();
	        Map<String, Object> hashMap = new HashMap<>();
	        List<Places>  placeList = db.find(Places.class).findList();
	        Collections.sort(placeList, pc);
	        hashMap.put("Places", placeList);
	        return ok(Json.toJson(hashMap));
	 	}
	 	
	 	
	 	public Result getAllPlaceValues() {
	        Map<String, Object> hashMap = new HashMap<>();
	        List<PlaceValues> valueList = db.find(PlaceValues.class).findList();
	        hashMap.put("PlaceValues", valueList);
	        return ok(Json.toJson(hashMap));
	 	}
	 	
	 	public Result getPlaceByID(){
	 		Map<String, Object> final_out=new HashMap<>();
			List<District> distList = db.find(District.class).findList();		
			
			for(District district : distList){
			Map<String, Object> dist_map=new HashMap<>();
			dist_map.put("dist_info", district);
			
			Map<String, Object> place_obj=new HashMap<>();	 		
			List<Places> placeList = db.find(Places.class).where().eq("id_dist", district.id_dist).findList();
			
			for(Places place:placeList){
			Map<String, Object> place_map=new HashMap<>();	
			place_map.put("place_info", place);
			List<PlaceValues> values = db.find(PlaceValues.class).where().eq("id_place", place.id_place).findList();
			place_map.put("values", values);
			place_obj.put(place.place_name, place_map);
			}
			dist_map.put("places", place_obj);
			final_out.put(district.district_name, dist_map);
			}
			return ok(Json.toJson(final_out));
	 	}
	 	
	 	public Result getOut(){
			Map<String, Object> final_out=new HashMap<>();
			List<District> distList = db.find(District.class).findList();
			
			for(District district : distList){
				Map<String, Object> dist_map=new HashMap<>();
				dist_map.put("dist_info", district);
				
				Map<String, Object> place_obj=new HashMap<>();
				List<Places> placeList = db.find(Places.class).where().eq("id_dist", district.id_dist).findList();
				for(Places place:placeList){
					Map<String, Object> place_map=new HashMap<>();
					if(district.id_dist==place.id_dist){
					place_map.put("place_info", place);
					}
					List<PlaceValues> valueList = db.find(PlaceValues.class).findList();
					for(PlaceValues value:valueList){
						if(place.id_place==value.id_place){
					    place_map.put("values", value);
					}}
					place_obj.put(place.place_name,place_map);
				}
				dist_map.put("places", place_obj);
				final_out.put(district.district_name, dist_map);	
				
			}
			
			return ok(Json.toJson(final_out));
		}
	        
	 	
	 	public Result getOut_two(){
			Map<String, Object> final_out=new HashMap<>();
			List<District> distList = db.find(District.class).findList();
			
			for(District district : distList){
				Map<String, Object> dist_map=new HashMap<>();
				Map<String, Object> place_obj=new HashMap<>();
				List<Places> placeList = db.find(Places.class).where().eq("id_dist", district.id_dist).findList();
				for(Places place:placeList){
					Map<String, Object> place_map=new HashMap<>();
					List<PlaceValues> valueList = db.find(PlaceValues.class).where().eq("id_place", place.id_place).findList();
					for(PlaceValues value:valueList){
					    place_map.put("values", value);
					}
					place_obj.put(place.place_name,place_map);
				}
				dist_map.put("places", place_obj);
				final_out.put(district.district_name, dist_map);	
				
			}
			
			return ok(Json.toJson(final_out));
		}
	       
	        
}
