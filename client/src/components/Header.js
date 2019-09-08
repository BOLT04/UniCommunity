import React from 'react'

import ReactMarkdown from 'react-markdown'
/**
 * Describes a Header functional React component
 * @param {object} props - The properties of this component. It must include the following:
 * {
 *     className: string -> This is the string with the CSS classes to be applied to the header.
 *     header: string    -> This is the text of the header.
 *     content: string   -> This is the text to display with the content.
 *     inMd: boolean     -> This represents whether or not the content is in markdown or not.
 * If it is then the proper component will be used to render it.
 * } 
 */
export default  ({ className, header, content, inMd }) => (
    <>
        <h4 className={className}>
            {header}
        </h4>
        {inMd ? <ReactMarkdown source={content} /> : <p>{content}</p> }
    </>
)
