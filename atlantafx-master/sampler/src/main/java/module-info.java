/* SPDX-License-Identifier: MIT */

module atlantafx.sampler {

    requires atlantafx.base;

    requires java.desktop;
    requires java.prefs;
    requires javafx.swing;
    requires javafx.media;
    requires javafx.web;
    requires javafx.fxml;
    requires jdk.zipfs;

    requires org.kordamp.ikonli.core;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.feather;
    requires org.kordamp.ikonli.material2;
    requires org.jetbrains.annotations;

    requires fr.brouillard.oss.cssfx;
    requires datafaker;
    requires java.sql;
    requires jbcrypt;
    requires mysql.connector.j;

    exports atlantafx.sampler;


    exports atlantafx.sampler.admin.layout;
    exports atlantafx.sampler.admin.page;
    exports atlantafx.sampler.admin.entity;

    exports atlantafx.sampler.admin.page.components;


    exports atlantafx.sampler.base.util;



    // resources
    opens atlantafx.sampler;
    opens atlantafx.sampler.assets.highlightjs;
    opens atlantafx.sampler.assets.styles;
    opens atlantafx.sampler.images;
    opens atlantafx.sampler.admin.entity;
//    requires org.scenicview.scenicview;
}
