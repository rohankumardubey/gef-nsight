package org.eclipse.gef.ui.palette;
/*
 * Licensed Material - Property of IBM
 * (C) Copyright IBM Corp. 2001, 2002 - All Rights Reserved.
 * US Government Users Restricted Rights - Use, duplication or disclosure
 * restricted by GSA ADP Schedule Contract with IBM Corp.
 */

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.ButtonGroup;
import org.eclipse.gef.*;
import org.eclipse.gef.palette.*;
import org.eclipse.gef.ui.palette.customize.*;
import org.eclipse.gef.ui.parts.PaletteViewerKeyHandler;
import org.eclipse.jface.viewers.*;

public class PaletteViewerImpl 
	extends org.eclipse.gef.ui.parts.GraphicalViewerImpl
	implements PaletteViewer
{

PaletteCustomizer customizer = null;
PaletteCustomizerDialog customizerDialog = null;
List paletteListeners = new ArrayList();
ButtonGroup buttonGroup = null;
PaletteEntry selectedEntry = null;
PaletteRoot paletteRoot = null;
PaletteViewerPreferences prefs = null;

public PaletteViewerImpl(){
	setEditDomain(new DefaultEditDomain(null));
	setKeyHandler(new PaletteViewerKeyHandler(this));
	setContextMenuProvider(new PaletteContextMenuProvider(this));
	setPaletteViewerPreferencesSource( new DefaultPaletteViewerPreferences(
						GEFPlugin.getDefault().getPreferenceStore()) );

}

public void addPaletteListener(PaletteListener paletteListener){
	if(paletteListeners!=null)
		paletteListeners.add(paletteListener);
}

public void addSelectionChangedListener(ISelectionChangedListener listener){;}

protected void createDefaultRoot(){
	setRootEditPart(new PaletteRootEditPart());
}

protected void firePaletteSelectionChanged(){
	if(paletteListeners==null)return;
	PaletteEvent event = new PaletteEvent(this, selectedEntry);
	for(int listener=0; listener<paletteListeners.size(); listener++)
		((PaletteListener)paletteListeners.get(listener)).entrySelected(event);
}

public ButtonGroup getButtonGroup(){
	if(buttonGroup==null)
		buttonGroup = new ButtonGroup();
	return buttonGroup;
}

/**
 * Returns the customizer.
 * @return PaletteCustomizer
 */
public PaletteCustomizer getCustomizer() {
	return customizer;
}

public PaletteCustomizerDialog getCustomizerDialog(){
	if (customizerDialog == null){
		customizerDialog = new PaletteCustomizerDialog( getControl().getShell(),
		                                    getCustomizer(), prefs, paletteRoot);
		
	}
	return customizerDialog;
}

public EditPartFactory getEditPartFactory(){
	if (super.getEditPartFactory() == null)
		setEditPartFactory(new PaletteEditPartFactory());
	return super.getEditPartFactory();
}

public ISelection getSelection(){return StructuredSelection.EMPTY;}

public PaletteToolEntry getSelectedEntry(){
	return (PaletteToolEntry)selectedEntry;
}

public void removePaletteListener(PaletteListener paletteListener){
	paletteListeners.remove(paletteListener);
}

public void removeSelectionChangedListener(ISelectionChangedListener listener){;}

/**
 * Sets the customizer.
 * @param customizer The customizer to set
 */
public void setCustomizer(PaletteCustomizer customizer) {
	this.customizer = customizer;
}

public void setPaletteRoot(PaletteRoot root){
	paletteRoot = root;
	if( paletteRoot != null){
		EditPart palette = getEditPartFactory().createEditPart(getRootEditPart(), root);
		getRootEditPart().setContents(palette);
	}
}

public void setPaletteViewerPreferencesSource(PaletteViewerPreferences prefs) {
	if( prefs == null ){
		return;
	}
	
	this.prefs = prefs;
}

/*
public void setSelection(ISelection newSelection){
	IStructuredSelection selected = (IStructuredSelection)newSelection;
	if(selected==null){
		if(getButtonGroup().getSelected()!=null)
			getButtonGroup().getSelected().setSelected(false);
	}else{
		Object entry = selected.getFirstElement();
		if(entry instanceof PaletteToolEntry)
			setSelection((PaletteEntry)entry);
	}
}*/

public void setSelection(PaletteEntry entry){
	if (selectedEntry == entry)
		return;
	if (entry == null){
		getButtonGroup().setSelected(null);
		selectedEntry = null;
		getButtonGroup().setSelected(getButtonGroup().getDefault());
		if (getButtonGroup().getSelected() == null){
			selectedEntry = null;
			firePaletteSelectionChanged();
		}
	} else {
		selectedEntry = entry;
		EntryEditPart ep = (EntryEditPart)getEditPartRegistry().get(entry);
		ep.select();
		firePaletteSelectionChanged();
	}
}

}


