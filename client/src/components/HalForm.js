import React from "react";

export const exampleHalFormsDocument = {
  _links: {
    self: {
      href: "http://api.example.org/rels/create",
    },
  },
  _templates: {
    default: {
      title: "Create",
      method: "POST",
      contentType: "application/json",
      properties: [
        {
          name: "title",
          required: true,
          value: "",
          prompt: "Title",
          regex: "",
          templated: false,
        },
        {
          name: "completed",
          required: false,
          value: "false",
          prompt: "Completed",
          regex: "",
        },
      ],
    },
  },
};

export function HalForm({ doc, onSubmit }) {
  return (
  <form method={doc._templates.default.method}>

  </form>
  )
}
