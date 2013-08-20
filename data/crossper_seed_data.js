db.business_categories.drop();
db.business_categories.insert({
	"name": "food  & drink",
     "subcategories": [
                        { "name":"African Restaurant","affinity_with": ["Bar", "Cafe", "shopping"]},
                        {"name": "Asian Restaurant",
                            "affinity_with": ["Ice Cream", "Services", "Pizza"]
                        },
                        { "name": "American Restaurant",
                            "affinity_with": ["Shopping", "Cafe", "Ice Cream"]
                        },
                        { "name": "Bar",
                            "affinity_with": ["Asian Restaurant", "American Restuarant", "Workout"]
                        },
                        { "name": "Bakery",  "affinity_with": ["Ice Cream", "Services", "Workout"]},
                        {
  "name": "Cafe",
  "affinity_with": ["Ice Cream", "Services", "Workout"]

},
{ "name": "Grocery Store",
	"affinity_with": ["Services", "Creative Classes", "Beauty"]
},
{"name": "Gelato Shop",
	"affinity_with": ["Shopping", "American Food", "Creative Classes"]
},
{"name": "Greek",
	"affinity_with": ["Shopping", "American Food", "Creative Classes"]
}
]});
db.business_categories.insert({
	"name": "Creative Classes",
                              "subcategories":[{ "name":"Dance School","affinity_with": []},{ "name":"Horse Riding School","affinity_with": []},{ "name":"Tennis Instructor","affinity_with": []},{ "name":"Swimming Instructor","affinity_with": []},{ "name":"Horse Riding Instructor","affinity_with": []},{ "name":"Skating Instructor","affinity_with": []},{ "name":"Martial Arts Instructor","affinity_with": []},{ "name":" Music Instructor","affinity_with": []},{ "name":"Ice Skating Rink","affinity_with": []},{ "name":"Golf Instructor","affinity_with": []},{ "name":"Guitar Instructor","affinity_with": []},{ "name":"Judo School","affinity_with": []},{ "name":"Karate school","affinity_with": []},{ "name":"Martial Arts School","affinity_with": []},{ "name":"Massage School","affinity_with": []}],
	"affinity_with": ["Beauty", "Shopping", "Cafe"]
});
db.business_categories.insert({
	"name": "Services",
      "subcategories": [{ "name":"Auto Repair Services","affinity_with": []},{ "name":"Bicycle shop and services","affinity_with": []},{ "name":"Bank","affinity_with": []},{ "name":"Child Care Services","affinity_with": []},{ "name":"Day Care Center","affinity_with": []},{ "name":"Dog Care Services","affinity_with": []},{ "name":"Equipment Rental Agency","affinity_with": []},{ "name":"Financial Services","affinity_with": []},{ "name":"Gas Station","affinity_with": []},{ "name":"Oil Change Service","affinity_with": []},{ "name":"Optometrist","affinity_with": []},{ "name":"Parking Lot","affinity_with": []},{ "name":"Paint Services","affinity_with": []}],
	"affinity_with": ["Asian", "American", "Cafe"]
});
db.business_categories.insert({
	"name": "Shopping",
      "subcategories": [{ "name":"Book Store","affinity_with": []},{ "name":"Car dealer","affinity_with": []},{ "name":"Cell phone store","affinity_with": []},{ "name":"Clothing Store","affinity_with": []},{ "name":"Golf Shop","affinity_with": []},{ "name":"Gift Shop","affinity_with": []},{ "name":"Florist","affinity_with": []},{ "name":"Garden Center","affinity_with": []},{ "name":"Hardware Store","affinity_with": []},{ "name":"Home Goods Store","affinity_with": []},{ "name":"Home Improvement Store","affinity_with": []},{ "name":"Linen Store","affinity_with": []},{ "name":"Magazine Store","affinity_with": []},{ "name":"Music Store","affinity_with": []},{ "name":"Party Store","affinity_with": []},{ "name":"Sport Store","affinity_with": []},{ "name":"Video Game Store","affinity_with": []},{ "name":"Video Rental Store","affinity_with": []},{ "name":"Watch Store","affinity_with": []}
        ]
    });
db.business_categories.insert({
	"name": "Beauty & Spa",
      "subcategories": [{"name":"Beauty Salon"},{"name":"Barber Shop"},{"name": "Facial Spa"},{"name":"Hair Salon"},{"name":"Massage Parlor"},{"name":"Tanning Salon"}],
	
});
db.business_categories.insert({
	"name": "Workout",
      "subcategories": [{"name":"Gym"}, {"name":"HealthClub"}],
	
});
db.business_categories.insert({
	"name": "Travel & Tours",
      "subcategories": [{"name": "Bed & Breakfast"},
             {"name" : " Car rental" }, {"name": "Hotel"}, {"name": "Hostel"} ]
	
});
db.business_categories.stats();

