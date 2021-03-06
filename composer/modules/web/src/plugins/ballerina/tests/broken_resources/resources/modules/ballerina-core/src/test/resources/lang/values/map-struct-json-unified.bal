struct Person {
    string name;
    int age;
    Person parent;
    json info;
    map address;
    int[] marks;
}

function testMultiValuedStructInlineInit() (Person) {
    Person p1 = { name:"aaa", age:25, 
                  parent:{name:"bbb", age:50}, 
                  address:{"city":"Colombo", "country":"SriLanka"}, 
                  info:{status:"single"}
                 };
    return p1;
}

function testAccessJsonInStruct() (string, string, string) {
    Person p1 = { name:"aaa",
                  age:25, 
                  parent:{ name:"bbb", 
                           age:50, 
                           address:{"city":"Colombo", "country":"SriLanka"}, 
                           info:{status:"married"}
                         },
                  info:{status:"single"}
                 };
    string statusKey = "status";
    string status1;
    string status2;
    string status3;
    status1, _ = (string)p1.parent.info.status;
    status2, _ = (string)p1["parent"]["info"]["status"];
    status3, _ = (string)p1["parent"].info["status"];
    return status1, status2, status3;
}

function testAccessMapInStruct() (any, any, any, string) {
    Person p1 = { name:"aaa",
                  age:25, 
                  parent:{ name:"bbb", 
                           age:50, 
                           address:{"city":"Colombo", "country":"SriLanka"}, 
                           info:{status:"married"}
                         },
                  info:{status:"single"}
                 };
    string cityKey = "city";
    string city;
    city, _ = (string) p1["parent"].address[cityKey];
    return p1.parent.address.city, p1["parent"]["address"]["city"], p1["parent"].address["city"], city;
}

function testSetValueToJsonInStruct() (json) {
    Person p1 = { name:"aaa",
                  age:25, 
                  parent:{ name:"bbb", 
                           age:50, 
                           address:{"city":"Colombo", "country":"SriLanka"}, 
                           info:{status:"married"}
                         },
                  info:{status:"single"}
                 };
    p1.parent.info.status = "widowed";
    p1["parent"]["info"]["retired"] = true;
    
    return p1["parent"].info;
}

function testAccessArrayInStruct() (int, int) {
    Person p1 = { marks:[87,94,72] };
    string statusKey = "status";
    return p1.marks[1], p1["marks"][2];
}

function testMapInitWithAnyType() (any, map) {
    any a = {name:"Supun"};
    map mapCast;
    mapCast, _ = (map)a;
    return a, mapCast;
}