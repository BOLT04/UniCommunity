'use strict'
import React from 'react'

/**
 * Describes a ForumPostHeader functional React component
 * @param {object} props - The properties of this component. It must include the following:
 * {
 *     post: object -> This is the model object that represents a Post.
 * } 
 */
export default function ForumPostHeader({ post: {author, createdAt} }) {
    return (
        <span style={{fontSize: 10, color: 'gray'}}>
            Published by <strong>{author}</strong> at {createdAt.toLocaleString()}  
        </span>
    )
}
