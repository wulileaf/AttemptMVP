package org.zackratos.attemptmvp.BaseMVP;

/**
 * Created by leaf on 2017/9/1.
 */

public abstract  class BaseModel<SubP>  {

    protected SubP mPresenter;

    public BaseModel(SubP presenter) {
        this.mPresenter = presenter;
    }
}
