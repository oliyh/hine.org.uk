(ns hine.org.uk.app
  (:require [reagent.core :as reagent]
            [re-frame.core :as re-frame]
            [goog.fx.dom.Scroll]))

(def println js/console.log)

(defn scroll-to! [id duration]
  (let [scroll-target (.getElementById js/document "parallax-container")
        target-y (int (.-scrollHeight (.getElementById js/document id)))
        current-y (fn [] (.-scrollTop scroll-target))
        finished? (fn [] (= target-y (current-y)))]
    (when-not (finished?)
      (let [counter (atom 0)
            step (/ Math/PI (/ duration 10))
            do-step (fn do-step []
                      (when-not (finished?)
                        (swap! counter inc)
                        (let [new-y (Math/ceil (+ (current-y)
                                                  (* (- target-y (current-y))
                                                     (- 0.5 (* 0.5 (Math/cos (* @counter step)))))))]
                          (.scrollTo scroll-target 0 new-y))
                        (.setTimeout js/window do-step 10)))]
        (do-step)))))

(defn- card [id title & [text actions]]
  [:div.mdl-card.mdl-shadow--2dp {:id (name id)}
   [:div.mdl-card__title
    [:h2.mdl-card__title-text title]]
   (when text
     [:div.mdl-card__supporting-text text])
   (when actions
     [:div.mdl-card__actions actions])])

(defn- jacqui-card []
  [card :jacqui-card "Jacqui" "Cakes and whatnot"])

(defn- andy-card []
  [card :andy-card "Andy" "Engineering MEng (Oxon) and Java developer"])

(defn- oliy-section []
  [:div#oliy-section.section
   [:div.mdl-grid
    [:div.mdl-cell.mdl-cell-4-col
     [card :oliy-developer "Developer"
      [:div
       [:p "Projects have included large financial applications for investment banks
           and "
        [:a {:href "https://onthemarket.com" :target "_blank"} "OnTheMarket.com"]
        ", one of the UK's largest property websites."]
       [:p "I am a firm believer in open source software and publish my work on GitHub."]]
      [:a.mdl-button.mdl-button--raised.mdl-button--colored
       {:href "https://github.com/oliyh" :target "_blank"}
       "View my GitHub profile"]]]
    [:div.mdl-cell.mdl-cell-4-col
     [card :oliy-developer "Photographer"
      [:div
       [:p "I am a passionate photographer and have been published by organisations including the BBC,
            the Guardian, WWF, Flickr Blog, and CBRE."]
       [:p "I tackle any subject, from landscapes, nature and weddings to macro and long exposure."]]
      [:div
       [:a.mdl-button.mdl-button--raised.mdl-button--colored
        {:href "https://flickr.com/photos/oliy/" :target "_blank"}
        "View on Flickr"]
       [:a.mdl-button.mdl-button--raised.mdl-button--colored
        {:href "https://www.facebook.com/oliverhinephotography/" :target "_blank"}
        "Follow on Facebook"]]]]
    [:div.mdl-cell.mdl-cell-4-col
     [card :oliy-developer "Optimist"
      [:div
       [:p "Although the media draws our focus primarily to negative events the world is full of good people
           being kind to one another. Nature's beauty is magnificent and the sun is always shining whether we
           see it or not."]
       [:p "Be good to each other and to the world."]]
      [:a.mdl-button.mdl-button--raised.mdl-button--colored
       {:href "https://www.google.co.uk/search?q=awesome+landscapes&tbm=isch" :target "_blank"}
       "Our planet"]]]]])

(defn- oliy-card []
  [:div {:on-click #(scroll-to! "oliy-section" 1000)}
   [card :oliy-card
    "Oliy"
    "Electronic Engineering MEng and Clojure/script developer with 8 years of industry experience"]])

(defn- home-grid []
  [:div#home-grid.mdl-grid
   [:div.mdl-cell.mdl-cell-4-col
    [jacqui-card]]
   [:div.mdl-cell.mdl-cell-4-col
    [andy-card]]
   [:div.mdl-cell.mdl-cell-4-col
    [oliy-card]]])

(defn site []
  [:div.mdl-layout
   #_[:header.mdl-layout__header.mdl-layout__header--transparent
    [:div.mdl-layout__header-row
     [:span.mdl-layout-title "hine.org.uk"]
     [:div.mdl-layout-spacer]
     [:nav.mdl-navigation
      [:a.mdl-navigation__link {:href "#"} "Home"]
      [:a.mdl-navigation__link {:href "#jacqui"} "Jacqui"]
      [:a.mdl-navigation__link {:href "#andy"} "Andy"]
      [:a.mdl-navigation__link {:href "#oliy"} "Oliy"]]]]
   [:main.mdl-layout__content

    [:div#parallax-container.parallax
     [:div#parallax-group-1.parallax-group
      [:div.parallax-layer.parallax-layer-base]
      [:div.parallax-layer.parallax-layer-fore
       [home-grid]]]

     [:div#parallax-group-2.parallax-group
      [:div.parallax-layer.parallax-layer-base]
      [:div.parallax-layer.parallax-layer-fore
       [oliy-section]]]]]])

(defn init []
  (reagent/render-component [site] (.getElementById js/document "container")))
