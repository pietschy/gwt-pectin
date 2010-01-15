package com.google.gwt.sample.contacts.client.presenter;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.sample.contacts.client.ContactsServiceAsync;
import com.google.gwt.sample.contacts.client.event.ContactUpdatedEvent;
import com.google.gwt.sample.contacts.client.event.EditContactCancelledEvent;
import com.google.gwt.sample.contacts.shared.Contact;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

public class EditContactPresenter implements Presenter
{
   public interface Display
   {
      HasClickHandlers getSaveButton();

      HasClickHandlers getCancelButton();

      void setContact(Contact contact);

      void commit();

      boolean validate();

      Widget asWidget();

   }

   private final ContactsServiceAsync rpcService;
   private final HandlerManager eventBus;
   private Display display;
   private Contact contact;

   public EditContactPresenter(ContactsServiceAsync rpcService, HandlerManager eventBus, Display display)
   {
      this.rpcService = rpcService;
      this.eventBus = eventBus;
      this.display = display;
      this.contact = new Contact();
      display.setContact(contact);
      bind();
   }

   public EditContactPresenter(ContactsServiceAsync rpcService, HandlerManager eventBus, Display display, String id)
   {
      this.rpcService = rpcService;
      this.eventBus = eventBus;
      this.display = display;
      bind();

      rpcService.getContact(id, new AsyncCallback<Contact>()
      {
         public void onSuccess(Contact result)
         {
            EditContactPresenter.this.contact = result;
            EditContactPresenter.this.display.setContact(result);
         }

         public void onFailure(Throwable caught)
         {
            Window.alert("Error retrieving contact");
         }
      });

   }

   public void bind()
   {
      this.display.getSaveButton().addClickHandler(new ClickHandler()
      {
         public void onClick(ClickEvent event)
         {
            doSave();
         }
      });

      this.display.getCancelButton().addClickHandler(new ClickHandler()
      {
         public void onClick(ClickEvent event)
         {
            eventBus.fireEvent(new EditContactCancelledEvent());
         }
      });
   }

   public void go(final HasWidgets container)
   {
      container.clear();
      container.add(display.asWidget());
   }

   private void doSave()
   {
      // ensure the contact is valid.
      if (display.validate())
      {
         // save changes to the contact
         display.commit();

         rpcService.updateContact(contact, new AsyncCallback<Contact>()
         {
            public void onSuccess(Contact result)
            {
               eventBus.fireEvent(new ContactUpdatedEvent(result));
            }

            public void onFailure(Throwable caught)
            {
               Window.alert("Error updating contact");
            }
         });
      }
   }

}
