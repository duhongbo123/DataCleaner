/**
 * eobjects.org DataCleaner
 * Copyright (C) 2010 eobjects.org
 *
 * This copyrighted material is made available to anyone wishing to use, modify,
 * copy, or redistribute it subject to the terms and conditions of the GNU
 * Lesser General Public License, as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License
 * for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this distribution; if not, write to:
 * Free Software Foundation, Inc.
 * 51 Franklin Street, Fifth Floor
 * Boston, MA  02110-1301  USA
 */
package org.eobjects.datacleaner.widgets.properties;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.inject.Inject;
import javax.swing.JComboBox;

import org.eobjects.analyzer.descriptors.ConfiguredPropertyDescriptor;
import org.eobjects.analyzer.job.builder.AbstractBeanJobBuilder;
import org.eobjects.analyzer.reference.ReferenceDataCatalog;
import org.eobjects.analyzer.reference.StringPattern;
import org.eobjects.datacleaner.widgets.ReferenceDataComboBoxListRenderer;

public class SingleStringPatternPropertyWidget extends AbstractPropertyWidget<StringPattern> {

	private static final long serialVersionUID = 1L;
	private final JComboBox _comboBox;

	@Inject
	public SingleStringPatternPropertyWidget(ConfiguredPropertyDescriptor propertyDescriptor,
			AbstractBeanJobBuilder<?, ?, ?> beanJobBuilder, ReferenceDataCatalog referenceDataCatalog) {
		super(beanJobBuilder, propertyDescriptor);


		_comboBox = new JComboBox();
		_comboBox.setRenderer(new ReferenceDataComboBoxListRenderer());
		_comboBox.setEditable(false);

		if (!propertyDescriptor.isRequired()) {
			_comboBox.addItem(null);
		}
		final String[] stringPatternNames = referenceDataCatalog.getStringPatternNames();
		for (String name : stringPatternNames) {
			_comboBox.addItem(referenceDataCatalog.getStringPattern(name));
		}

		StringPattern currentValue = (StringPattern) beanJobBuilder.getConfiguredProperty(propertyDescriptor);
		_comboBox.setSelectedItem(currentValue);

		_comboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				fireValueChanged();
			}
		});
		add(_comboBox);
	}

	@Override
	public StringPattern getValue() {
		return (StringPattern) _comboBox.getSelectedItem();
	}

	@Override
	protected void setValue(StringPattern value) {
		_comboBox.setEditable(true);
		_comboBox.setSelectedItem(value);
		_comboBox.setEditable(false);
	}
}
