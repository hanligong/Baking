package com.baking.action.baking.model;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

import java.util.List;

/**
 * Created by hanyuezi on 17/12/7.
 */
@JsonObject
public class HttpObject {

    @JsonField
    private List<RecipeModel> list;

    public List<RecipeModel> getList() {
        return list;
    }

    public void setList(List<RecipeModel> list) {
        this.list = list;
    }
}
