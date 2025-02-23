package com.example.myapplication1;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class GuidelinesActivity extends AppCompatActivity {

    private ArrayList<GuidelineItem> guidelineList;
    private GuidelineAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guidelines);

        ListView listView = findViewById(R.id.listView);

        // Data for ListView
        guidelineList = new ArrayList<>();
        guidelineList.add(new GuidelineItem("Blizzard", "➡️INFO: A blizzard is a severe snowstorm with strong winds and low visibility, causing extreme cold and dangerous travel conditions. It can lead to frostbite, hypothermia, and isolation.\n\n✅DO’s:\n️️➡️Stay Indoors – Keep warm and avoid exposure.\n️️➡️ Layer Up – Wear multiple layers of dry, insulated clothing.\n➡️ Keep Moving – Stay active to maintain body heat.\n➡️ Melt Snow for Water – If stranded, melt snow before drinking.\n➡️ Check for Frostbite – Numbness or pale skin needs warming.\n" +
                "\n❌DONT's:\n➡️ Don’t Stay in the Open – Risk of hypothermia and frostbite.\n➡️ Don’t Overexert – Sweating can freeze and lower body temperature.\n➡️ Don’t Eat Snow Directly – Lowers body temperature; melt it first.\n➡️ Don’t Use Open Flames Indoors – Carbon monoxide poisoning risk.\n"
        ));

        guidelineList.add(new GuidelineItem("Drought", "➡️INFO: A drought is a prolonged period of little or no rainfall, leading to water shortages, dry soil, and reduced crop growth. It can cause famine, wildfires, and economic losses\n\n✅Do’s:\n➡️ Hydrate Immediately – Provide small sips of clean water.\n➡️ Find Shade – Move to a cool, shaded area.\n➡️ Use Wet Cloth – Dab skin with a damp cloth to cool down.\n➡️ Seek Medical Help – Call emergency services if severe dehydration signs appear.\n" +
                "\n❌Dont's:\n➡️ Don’t Gulp Water Fast – Can cause shock; drink slowly.\n➡️ Don’t Stay in the Sun – Avoid direct heat exposure.\n➡️ Don’t Ignore Symptoms – Dizziness, dry mouth, or confusion need urgent care.\n➡️ Don’t Drink Contaminated Water – Risk of diseases; purify if needed.\n"
        ));
        guidelineList.add(new GuidelineItem("Earthquake", "➡️INFO: An earthquake is a sudden shaking of the ground caused by movements in the Earth's crust. It can lead to building collapses, landslides, and tsunamis.\n\n✅Do’s:\n➡️ Drop, Cover, and Hold – Take cover under sturdy furniture.\n➡️ Stay Indoors – If inside, move away from windows and heavy objects.\n➡️ Protect Your Head – Use hands, pillows, or books to shield from debris.\n➡️ If Outside – Move to an open area away from buildings and power lines.\n➡️ After Shaking Stops – Check for injuries and evacuate if necessary.\n" +
                "\n❌Dont's:\n➡️ Don’t Use Elevators – They may get stuck or malfunction.\n➡️ Don’t Run Outside – Falling debris poses a serious threat.\n➡️ Don’t Stand Near Glass – Windows and mirrors can shatter.\n➡️ Don’t Light Flames – Gas leaks may cause explosions.\n"
        ));
        guidelineList.add(new GuidelineItem("Flood", "➡️INFO: A flood is an overflow of water that submerges land, caused by heavy rainfall, storms, or dam failures. It can lead to drowning, property damage, and waterborne diseases.\n\n✅Do’s:\n➡️ Move to Higher Ground – Stay above floodwaters.\n➡️ Turn Off Electricity & Gas – Prevent electrocution and explosions.\n➡️ Store Clean Water – Prevent dehydration and waterborne diseases.\n➡️ Follow Official Alerts – Evacuate if instructed by authorities.\n➡️ Use a Stick to Check Depth – Floodwaters may hide open drains.\n" +
                "\n❌Dont's:\n➡️ Don’t Walk or Drive Through Floodwater – Just 6 inches can knock you over.\n➡️ Don’t Touch Electrical Appliances – Risk of electrocution.\n➡️ Don’t Drink Contaminated Water – Boil or purify before consuming.\n➡️ Don’t Return Home Until Safe – Wait for official clearance.\n"
        ));
        guidelineList.add(new GuidelineItem("Heatwave", "➡️INFO: A heatwave is a prolonged period of excessively high temperatures, which can cause dehydration, heat exhaustion, and heatstroke.\n\n✅Do’s:\n➡️ Stay Hydrated – Drink plenty of water regularly.\n➡️ Stay Indoors – Keep cool in shaded or air-conditioned areas.\n➡️ Wear Light Clothing – Loose, light-colored clothes help stay cool.\n➡️ Eat Light Meals – Avoid heavy and hot foods.\n➡️ Check on Vulnerable People – Infants, elderly, and sick people need extra care.\n" +
                "\n❌Dont's:\n➡️ Don’t Go Out in Peak Sun Hours – Avoid exposure from 12 PM to 4 PM.\n➡️ Don’t Drink Caffeinated or Sugary Beverages – They dehydrate the body.\n➡️ Don’t Engage in Strenuous Activities – Avoid excessive exercise outdoors.\n➡️ Don’t Leave Children or Pets in Cars – Risk of fatal heatstroke.\n"
        ));
        guidelineList.add(new GuidelineItem("Landslide", "➡️INFO: A landslide is the sudden movement of rock, soil, and debris down a slope, often triggered by heavy rain, earthquakes, or human activities. It can bury homes, roads, and cause fatalities.\n\n✅Do’s:\n➡️ Move to Higher Ground – Stay away from slopes and unstable areas.\n➡️ Listen to Warnings – Follow evacuation orders and alerts.\n➡️ Watch for Warning Signs – Cracks in the ground, tilting trees, or unusual water flow.\n➡️ Protect Your Head – Use arms or sturdy objects to shield from debris.\n➡️ Help Others – Assist those in need and report missing persons.\n" +
                "\n❌Dont's:\n➡️ Don’t Stay in Low Areas – Risk of being buried by debris.\n➡️ Don’t Ignore Cracks or Unstable Slopes – Evacuate immediately.\n➡️ Don’t Walk or Drive Through Landslide Zones – The ground may still be shifting.\n➡️ Don’t Return Until Declared Safe – Authorities will assess stability before allowing re-entry.\n"
        ));
        guidelineList.add(new GuidelineItem("Sandstorm", "➡️INFO: A sandstorm is a strong windstorm that carries dust and sand particles, reducing visibility and causing breathing difficulties. It can lead to accidents, health issues, and damage to infrastructure.\n\n✅Do’s:\n➡️ Seek Shelter – Stay indoors or in a covered area.\n➡️ Cover Nose & Mouth – Use a mask, scarf, or damp cloth to filter dust.\n➡️ Protect Eyes – Wear goggles or wraparound sunglasses.\n➡️ Stay Low – If caught outside, crouch behind a barrier to avoid strong winds.\n➡️ Seal Openings – Close windows, doors, and vents to keep dust out.\n" +
                "\n❌Dont's:\n➡️ Don’t Drive Unless Necessary – Low visibility increases accident risks.\n➡️ Don’t Breathe Unfiltered Air – Dust inhalation can cause respiratory issues.\n➡️ Don’t Rub Your Eyes – Wash them with clean water if sand gets in.\n➡️ Don’t Use Contact Lenses – Sand particles can cause severe irritation.\n"
        ));
        guidelineList.add(new GuidelineItem("Thunderstorm", "➡️INFO: A thunderstorm is a weather event with heavy rain, lightning, thunder, strong winds, and sometimes hail. It can cause power outages, flooding, and fires from lightning strikes.\n\n✅Do’s:\n➡️ Stay Indoors – Seek shelter in a sturdy building.\n➡️ Unplug Electrical Devices – Protect them from power surges.\n➡️ Stay Away from Windows – Strong winds and hail can break glass.\n➡️ Avoid Plumbing – Don’t use sinks or showers as lightning can travel through pipes.\n➡️ Listen to Weather Alerts – Stay updated on warnings and safety instructions.\n" +
                "\n❌Dont's:\n➡️ Don’t Stay in Open Fields – Risk of being struck by lightning.\n➡️ Don’t Take Shelter Under Trees – Lightning often strikes tall objects.\n➡️ Don’t Use Metal Objects – Avoid umbrellas, bicycles, and fences.\n➡️ Don’t Drive Through Flooded Roads – Water depth can be misleading and dangerous.\n"
        ));
        guidelineList.add(new GuidelineItem("Tornado", "➡️INFO: A tornado is a rapidly rotating column of air that extends from a thunderstorm to the ground, causing extreme winds, flying debris, and destruction.\n\n✅Do’s:\n➡️ Take Shelter Immediately – Move to a basement or an interior room without windows.\n➡️ Cover Your Head – Use a helmet, mattress, or your arms for protection.\n➡️ Stay Low – Lie flat in a low-lying area if no shelter is available.\n➡️ Listen to Alerts – Follow weather warnings and emergency broadcasts.\n➡️ Secure Doors & Windows – Re➡️INFOrce them if time permits.\n" +
                "\n❌Dont's:\n➡️ Don’t Stay in Vehicles or Mobile Homes – They can be easily lifted and destroyed.\n➡️ Don’t Open Windows – It won’t equalize pressure and may let debris in.\n➡️ Don’t Take Shelter Under a Bridge – Wind speeds increase under structures.\n➡️ Don’t Go Outside to Watch – Flying debris can cause fatal injuries.\n"
        ));
        guidelineList.add(new GuidelineItem("Tsunami", "➡️INFO: A tsunami is a series of large ocean waves caused by underwater earthquakes, volcanic eruptions, or landslides. It can flood coastal areas, destroy infrastructure, and cause massive casualties.\n\n✅Do’s:\n➡️ Move to Higher Ground – Evacuate immediately to elevated areas.\n➡️ Follow Official Warnings – Listen to emergency alerts and sirens.\n➡️ Stay Away from Shorelines – A receding ocean is a tsunami warning sign.\n➡️ Grab an Emergency Kit – Carry essentials like water, food, and medical supplies.\n➡️ Help Others – Assist children, elderly, and disabled individuals during evacuation.\n" +
                "\n❌Dont's:\n➡️ Don’t Stay Near the Coast – Even after initial waves, more can follow.\n➡️ Don’t Wait for Confirmation – If you feel strong shaking near the coast, evacuate immediately.\n➡️ Don’t Use Vehicles on Flooded Roads – Waves can sweep them away.\n➡️ Don’t Return Until Authorities Declare Safe – Floodwaters and debris can remain dangerous.\n"
        ));
        guidelineList.add(new GuidelineItem("Volcanic Eruption", "➡️INFO: A volcanic eruption is the release of lava, ash, and gases from a volcano, which can cause fires, toxic air, landslides, and widespread destruction.\n\n✅Do’s:\n➡️ Evacuate Immediately – Follow official evacuation orders.\n➡️ Cover Nose & Mouth – Use a mask or damp cloth to avoid inhaling ash.\n➡️ Stay Indoors – Keep windows and doors closed to prevent ash from entering.\n➡️ Wear Protective Clothing – Use long sleeves, pants, and goggles to protect skin and eyes.\n➡️ Listen to Alerts – Follow emergency broadcasts for updates and safety instructions.\n" +
                "\n❌Dont's:\n➡️ Don’t Stay Near the Volcano – Ash, lava, and toxic gases can be deadly.\n➡️ Don’t Drive in Heavy Ashfall – It reduces visibility and can damage engines.\n➡️ Don’t Drink Contaminated Water – Ashfall can pollute water sources.\n➡️ Don’t Ignore Warning Signs – Tremors, gas emissions, and rumbling indicate danger.\n"
        ));
        guidelineList.add(new GuidelineItem("Wildfire", "➡️INFO: A wildfire is an uncontrolled fire that spreads rapidly in forests, grasslands, or rural areas. It can destroy homes, cause air pollution, and lead to fatalities.\n\n✅Do’s:\n➡️ Evacuate Immediately – Follow official orders and leave early.\n➡️ Cover Nose & Mouth – Use an N95 mask or damp cloth to avoid smoke inhalation.\n➡️ Stay Low – Crawl if there is heavy smoke to avoid toxic fumes.\n➡️ Wet Clothes & Towels – Helps protect from heat and embers.\n➡️ Keep Emergency Kit Ready – Include water, food, flashlight, and important documents.\n" +
                "\n❌Dont's:\n➡️ Don’t Stay in Fire’s Path – Fires spread fast; leave while there’s time.\n➡️ Don’t Breathe in Smoke – Can cause suffocation and lung damage.\n➡️ Don’t Use Flammable Materials – Avoid gasoline, alcohol, or aerosol cans.\n➡️ Don’t Return Until Safe – Wait for official clearance to go back home.\n"
        ));
        adapter = new GuidelineAdapter(this, guidelineList);
        listView.setAdapter(adapter);

        // Handle ListView item click
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Toggle dropdown visibility
                guidelineList.get(position).toggleExpanded();
                adapter.notifyDataSetChanged();
            }
        });
    }
}
