import React from 'react'

export default function ForumPostHeader({ authorName, createdAt }) {
    return (
        <span style={{fontSize: 13, color: 'gray'}}>
            Published by <strong>{authorName}</strong> at {createdAt.toLocaleString()}  
        </span>
    )
}
