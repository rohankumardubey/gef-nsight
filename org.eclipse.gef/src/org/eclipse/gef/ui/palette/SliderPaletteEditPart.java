package org.eclipse.gef.ui.palette;
/*
 * Licensed Material - Property of IBM
 * (C) Copyright IBM Corp. 2001, 2002 - All Rights Reserved.
 * US Government Users Restricted Rights - Use, duplication or disclosure
 * restricted by GSA ADP Schedule Contract with IBM Corp.
 */

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.ToolbarLayout;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.palette.PaletteRoot;

public class SliderPaletteEditPart 
	extends PaletteEditPart
{

public SliderPaletteEditPart(PaletteRoot paletteRoot){
	super(paletteRoot);
}

public IFigure createFigure(){
	Figure figure = new Figure();
	figure.setBorder(new MarginBorder(2,0,2,0));
	figure.setForegroundColor(ColorConstants.listForeground);
	figure.setBackgroundColor(ColorConstants.listBackground);
	ToolbarLayout layout = new ToolbarLayout();
	layout.setSpacing(3);
	figure.setLayoutManager(layout);
	return figure;
}

protected void addChildVisual(EditPart childEditPart, int index){
	((GraphicalEditPart)childEditPart).getFigure().setBorder(new PaletteDrawerBorder());
	super.addChildVisual(childEditPart, index);
}

}