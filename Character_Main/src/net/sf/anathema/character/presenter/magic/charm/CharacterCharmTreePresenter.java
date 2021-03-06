package net.sf.anathema.character.presenter.magic.charm;

import net.sf.anathema.character.generic.magic.charms.GroupCharmTree;
import net.sf.anathema.character.main.model.charms.CharmsModel;
import net.sf.anathema.character.presenter.magic.CharacterAlienCharmPresenter;
import net.sf.anathema.character.presenter.magic.CharacterCharmGroupChangeListener;
import net.sf.anathema.character.presenter.magic.CharacterCharmModel;
import net.sf.anathema.character.presenter.magic.CharacterCharmTreeViewProperties;
import net.sf.anathema.character.presenter.magic.CharacterCharmTypes;
import net.sf.anathema.character.presenter.magic.CharacterGroupCharmTree;
import net.sf.anathema.character.presenter.magic.CharacterGroupCollection;
import net.sf.anathema.character.presenter.magic.CharacterSpecialCharmPresenter;
import net.sf.anathema.character.presenter.magic.CommonSpecialCharmList;
import net.sf.anathema.character.presenter.magic.LearnInteractionPresenter;
import net.sf.anathema.character.presenter.magic.SpecialCharmList;
import net.sf.anathema.character.presenter.magic.SpecialCharmViewBuilder;
import net.sf.anathema.character.presenter.magic.detail.ShowMagicDetailListener;
import net.sf.anathema.charmtree.presenter.AbstractCascadePresenter;
import net.sf.anathema.charmtree.presenter.CharacterColoringStrategy;
import net.sf.anathema.charmtree.presenter.ConfigurableCharmDye;
import net.sf.anathema.charmtree.view.CharmDisplayPropertiesMap;
import net.sf.anathema.charmtree.view.DefaultNodeProperties;
import net.sf.anathema.charmtree.view.ICharmView;
import net.sf.anathema.lib.resources.Resources;
import net.sf.anathema.lib.util.Identifier;
import net.sf.anathema.platform.tree.document.visualizer.ITreePresentationProperties;

public class CharacterCharmTreePresenter extends AbstractCascadePresenter {

  private final ICharmView view;
  private final CharacterCharmModel model;

  public CharacterCharmTreePresenter(Resources resources, ICharmView view, CharacterCharmModel charmModel,
                                     ITreePresentationProperties presentationProperties, CharmDisplayPropertiesMap displayPropertiesMap) {
    super(resources);
    this.model = charmModel;
    CharmsModel charmConfiguration = model.getCharmConfiguration();
    CharacterCharmTreeViewProperties viewProperties =
            new CharacterCharmTreeViewProperties(resources, charmConfiguration, model.getMagicDescriptionProvider());
    DefaultNodeProperties nodeProperties = new DefaultNodeProperties(resources, viewProperties, viewProperties);
    this.view = view;
    view.initGui(viewProperties, nodeProperties);
    CharacterCharmGroupChangeListener charmGroupChangeListener =
            new CharacterCharmGroupChangeListener(charmConfiguration, view.getCharmTreeRenderer(), displayPropertiesMap);
    ConfigurableCharmDye colorist = new ConfigurableCharmDye(charmGroupChangeListener, new CharacterColoringStrategy(presentationProperties.getColor(), view, model));
    setCharmTypes(new CharacterCharmTypes(charmModel));
    setChangeListener(charmGroupChangeListener);
    setView(view);
    SpecialCharmViewBuilder specialViewBuilder = createSpecialCharmViewBuilder(resources, charmConfiguration);
    SpecialCharmList specialCharmList = new CommonSpecialCharmList(view, specialViewBuilder);
    setSpecialPresenter(new CharacterSpecialCharmPresenter(charmGroupChangeListener, charmModel, specialCharmList));
    setCharmDye(colorist);
    setAlienCharmPresenter(new CharacterAlienCharmPresenter(model, view));
    setInteractionPresenter(new LearnInteractionPresenter(model, view, viewProperties, colorist));
    setCharmGroups(new CharacterGroupCollection(model));
  }

  private SpecialCharmViewBuilder createSpecialCharmViewBuilder(Resources resources, CharmsModel charmConfiguration) {
    return new SwingSpecialCharmViewBuilder(resources, charmConfiguration);
  }

  @Override
  protected GroupCharmTree getCharmTree(Identifier cascadeType) {
    return new CharacterGroupCharmTree(model, cascadeType);
  }

  @SuppressWarnings("UnusedDeclaration")
  public void addShowDetailListener(ShowMagicDetailListener listener) {
    view.addCharmInteractionListener(new ShowDetailInteractionListener(listener));
  }
}
