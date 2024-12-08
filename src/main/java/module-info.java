//module com.example.bankingsystem {
//    requires javafx.controls;
//    requires javafx.fxml;
//    requires java.sql;
//    requires junit;
//    requires spring.web;
//    requires spring.beans;
//    requires spring.boot;
//    requires spring.boot.autoconfigure;
//
//
//    opens com.example.bankingsystem to javafx.fxml;
//    exports com.example.bankingsystem;
//    exports com.example.bankingsystem.ui;
//    opens com.example.bankingsystem.ui to javafx.fxml;
//}
module com.example.bankingsystem {
    requires spring.boot;
    requires spring.boot.autoconfigure;
    requires spring.context;
    requires spring.beans;
    requires spring.core;
    requires spring.data.jpa;
    requires java.sql;
    requires javafx.controls;
    requires javafx.fxml;
    requires spring.web;
    requires mysql.connector.j;


    opens com.example.bankingsystem to spring.core, spring.beans, spring.context;
    opens com.example.bankingsystem.controller to spring.core, spring.beans, spring.context;
    opens com.example.bankingsystem.db to spring.core, spring.beans, spring.context;
    opens com.example.bankingsystem.model to spring.core, spring.beans, spring.context;
    opens com.example.bankingsystem.service to spring.core, spring.beans, spring.context;
}
