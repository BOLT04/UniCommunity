'use strict'
import React from 'react'

/**
 * Describes a Header functional React component
 * @param {object} props - The properties of this component. It must include the following:
 * {
 *     className: string -> This is the string with the CSS classes to be applied to the header.
 *     header: string    -> This is the text of the header.
 *     content: string   -> This is the paragraph text.
 * } 
 */
export default function Header({ className, header, content }) {
    return (
        <>
            <h4 className={className}>
                {header}
            </h4>
            <p>{content}</p>
        </>
    )
}
