package com.kd.openplatform.charge.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "api_charge_account", catalog = "")
public class ChargeAccountEntity implements  java.io.Serializable{
    private String id;
    private String restState;
    private String typename;
    private String apiAppRelaId;

    @Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
    @Column(name = "id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "rest_state")
    public String getRestState() {
        return restState;
    }

    public void setRestState(String restState) {
        this.restState = restState;
    }

    @Basic
    @Column(name = "typename")
    public String getTypename() {
        return typename;
    }

    public void setTypename(String typename) {
        this.typename = typename;
    }

    @Basic
    @Column(name = "api_app_rela_id")
    public String getApiAppRelaId() {
		return apiAppRelaId;
	}

	public void setApiAppRelaId(String apiAppRelaId) {
		this.apiAppRelaId = apiAppRelaId;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ChargeAccountEntity that = (ChargeAccountEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (restState != null ? !restState.equals(that.restState) : that.restState != null) return false;
        if (typename != null ? !typename.equals(that.typename) : that.typename != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (restState != null ? restState.hashCode() : 0);
        result = 31 * result + (typename != null ? typename.hashCode() : 0);
        return result;
    }
//
//    public ApiAppRelaEntity getApiAppRelaEntity() {
//        return apiAppRelaEntity;
//    }
//
//    public void setApiAppRelaEntity(ApiAppRelaEntity apiAppRelaEntity) {
//        this.apiAppRelaEntity = apiAppRelaEntity;
//    }
}
