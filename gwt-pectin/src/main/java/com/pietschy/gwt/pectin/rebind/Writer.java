package com.pietschy.gwt.pectin.rebind;

import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.user.rebind.SourceWriter;

/**
* Created by IntelliJ IDEA.
* User: andrew
* Date: Jul 2, 2010
* Time: 9:58:06 AM
* To change this template use File | Settings | File Templates.
*/
public class Writer
{
   private SourceWriter writer;

   public Writer(SourceWriter writer)
   {
      this.writer = writer;
   }

   public void print(String string)
   {
      writer.print(string);
   }

   public void println(String string)
   {
      writer.println(string);
   }

   public void println(String format, Object... args)
   {
      writer.println(String.format(format, args));
   }

   public void indent()
   {
      writer.indent();
   }

   public void outdent()
   {
      writer.outdent();
   }

   public void endJavaDocComment()
   {
      writer.endJavaDocComment();
   }

   public void beginJavaDocComment()
   {
      writer.beginJavaDocComment();
   }

   public void commit(TreeLogger logger)
   {
      writer.commit(logger);
   }

   public void println()
   {
      writer.println();
   }
}
