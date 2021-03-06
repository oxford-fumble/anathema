package net.sf.anathema.framework.persistence;

import net.sf.anathema.framework.repository.Item;
import net.sf.anathema.framework.repository.RepositoryException;
import net.sf.anathema.framework.repository.access.IRepositoryReadAccess;
import net.sf.anathema.framework.repository.access.IRepositoryWriteAccess;
import net.sf.anathema.lib.exception.PersistenceException;
import net.sf.anathema.lib.workflow.wizard.selection.IDialogModelTemplate;

import java.io.IOException;

public interface RepositoryItemPersister {

  void save(IRepositoryWriteAccess writeAccess, Item item) throws IOException, RepositoryException;

  Item load(IRepositoryReadAccess readAccess) throws PersistenceException, RepositoryException;

  Item createNew(IDialogModelTemplate template) throws PersistenceException;
}