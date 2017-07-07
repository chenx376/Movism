package me.chenyongrui.movism.data.api.model.tmdb;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class CastsData implements Serializable
{

@SerializedName("id")
@Expose
private Integer id;
@SerializedName("cast")
@Expose
private List<Cast> cast = null;
@SerializedName("crew")
@Expose
private List<Crew> crew = null;
private final static long serialVersionUID = -3910119576261357126L;

public Integer getId() {
return id;
}

public void setId(Integer id) {
this.id = id;
}

public List<Cast> getCast() {
return cast;
}

public void setCast(List<Cast> cast) {
this.cast = cast;
}

public List<Crew> getCrew() {
return crew;
}

public void setCrew(List<Crew> crew) {
this.crew = crew;
}

}