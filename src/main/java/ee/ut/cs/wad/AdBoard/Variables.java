package ee.ut.cs.wad.AdBoard;

import org.springframework.ui.Model;

import java.util.HashMap;
import java.util.Map;
/*
vozvraschaet peremennqi iz html koda, chto bq ne pisat odno i toze neskolko raz. Luche etogo nichego ne pridumal
 */

public class Variables {

    private Map<String,String> map = new HashMap();

    public Variables(){
        map.put("about_us","about");
        map.put("find_work","findWork");
        map.put("home","home");
        map.put("offer_work","offerWork");
        map.put("sign_in","signIn");
        map.put("sign_up","signUp");
        map.put("navbar","#");

    }

    public void turn_nav_barOn(Model model){
        model.addAllAttributes(map);
    }

    public String getVariableValue(String nameOfVariable){
        return map.getOrDefault(nameOfVariable,"#");
    }
}
