package net.sf.anathema.character.model.advance.models;

import net.sf.anathema.character.presenter.overview.IOverviewModel;
import net.sf.anathema.lib.util.SimpleIdentifier;

public abstract class AbstractOverviewModel extends SimpleIdentifier implements IOverviewModel {

  private final String categoryId;

  public AbstractOverviewModel(String categoryId, String id) {
    super(id);
    this.categoryId = categoryId;
  }

  @Override
  public String getCategoryId() {
    return categoryId;
  }
}