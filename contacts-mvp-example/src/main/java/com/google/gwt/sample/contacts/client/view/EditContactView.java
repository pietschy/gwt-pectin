package com.google.gwt.sample.contacts.client.view;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.sample.contacts.client.presenter.EditContactPresenter;
import com.google.gwt.sample.contacts.shared.Contact;
import com.google.gwt.user.client.ui.*;
import com.pietschy.gwt.pectin.client.form.binding.FormBinder;
import com.pietschy.gwt.pectin.client.form.validation.binding.ValidationBinder;
import com.pietschy.gwt.pectin.client.form.validation.component.ValidationDisplayPanel;

public class EditContactView extends Composite implements EditContactPresenter.Display
{
   private EditContactModel model;

   private final TextBox firstName;
   private final TextBox lastName;
   private final TextBox emailAddress;
   private final FlexTable detailsTable;
   private final Button saveButton;
   private final Button cancelButton;

   private FormBinder binder = new FormBinder();
   private ValidationBinder validation = new ValidationBinder();


   public EditContactView()
   {
      this(new EditContactModel());
   }

   public EditContactView(EditContactModel model)
   {
      this.model = model;
      DecoratorPanel contentDetailsDecorator = new DecoratorPanel();
      contentDetailsDecorator.setWidth("18em");
      initWidget(contentDetailsDecorator);

      VerticalPanel contentDetailsPanel = new VerticalPanel();
      contentDetailsPanel.setWidth("100%");

      // add our validation messages
      ValidationDisplayPanel validationMessages = new ValidationDisplayPanel();
      contentDetailsPanel.add(validationMessages);

      // Create the contacts list
      //
      detailsTable = new FlexTable();
      detailsTable.setCellSpacing(0);
      detailsTable.setWidth("100%");
      detailsTable.addStyleName("contacts-ListContainer");
      detailsTable.getColumnFormatter().addStyleName(1, "add-contact-input");
      firstName = new TextBox();
      lastName = new TextBox();
      emailAddress = new TextBox();

      initDetailsTable();
      contentDetailsPanel.add(detailsTable);

      HorizontalPanel menuPanel = new HorizontalPanel();
      saveButton = new Button("Save");
      cancelButton = new Button("Cancel");
      menuPanel.add(saveButton);
      menuPanel.add(cancelButton);
      contentDetailsPanel.add(menuPanel);
      contentDetailsDecorator.add(contentDetailsPanel);

      binder.bind(model.firstName).to(firstName);
      binder.bind(model.lastName).to(lastName);
      binder.bind(model.emailAddress).to(emailAddress);

      validation.bindValidationOf(model).to(validationMessages);
   }

   private void initDetailsTable()
   {
      detailsTable.setWidget(0, 0, new Label("Firstname"));
      detailsTable.setWidget(0, 1, firstName);
      detailsTable.setWidget(1, 0, new Label("Lastname"));
      detailsTable.setWidget(1, 1, lastName);
      detailsTable.setWidget(2, 0, new Label("Email Address"));
      detailsTable.setWidget(2, 1, emailAddress);
      firstName.setFocus(true);
   }

   public HasClickHandlers getSaveButton()
   {
      return saveButton;
   }

   public HasClickHandlers getCancelButton()
   {
      return cancelButton;
   }

   public void setContact(Contact contact)
   {
      model.setContact(contact);
   }

   public void commit()
   {
      model.commit();
   }

   public boolean validate()
   {
      return model.validate();
   }

   public Widget asWidget()
   {
      return this;
   }
}
