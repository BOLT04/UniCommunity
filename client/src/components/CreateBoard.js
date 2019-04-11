import React, { Component } from 'react'
import { Input, Form, TextArea, Button } from 'semantic-ui-react'

import BoardTemplate from './BoardTemplate'

const boardDummy = {
  name: "PDM",
  modules: [
    {
      name: "Sumarios",
      content: [
        {
          name: "18/02/2019 - Course introduction",
          text: `* Syllabus, teaching methodology and bibliography.\n  * [Evaluation](https://github.com/isel-leic-daw/1819v-public/wiki/evaluation)\n  * [Resources](https://github.com/isel-leic-daw/1819v-public/wiki/resources)
          `
        },
        {
          name: "20/02/2019 - Designing Web APIs: Introduction",
          text: `* Web APIs (or HTTP APIs): Concept and Motivation\n* The [Architecture of the World Wide Web](https://www.w3.org/TR/webarch/)\n* The HTTP protocol: Introduction\n* Documentation:\n  * ["Introduction to Web APIs"](https://github.com/isel-leic-daw/1819v-public/wiki/Web-APIs)\n  * ["Designing evolvable Web APIs: Chapter 1"](https://www.oreilly.com/library/view/designing-evolvable-web/9781449337919/ch01.html)`
        }
      ]
    },
    {
      name: "Sumarios",
      content: [
        {
          name: "18/02/2019 - Course introduction",
          text: `* Syllabus, teaching methodology and bibliography.\n  * [Evaluation](https://github.com/isel-leic-daw/1819v-public/wiki/evaluation)\n  * [Resources](https://github.com/isel-leic-daw/1819v-public/wiki/resources)
          `
        }
      ]
    }
  ]
}


export default class CreateBoard extends Component {
  //TODO: should these props be in the object "state" of react?? and use this.setState()????
  titleVal = ""
  descVal = ""
  templates = [] //array<string>

  onTitleChange = e => {
    this.titleVal = e.target.value
  }

  onDescriptionChange = e => {
    this.descVal = e.target.value
  }

  submitCreateBoardReq() {
    console.log(this.descVal)
    console.log(this.titleVal)

    this.boardTemplate.updatePropsTemplates()
    console.log(this.templates)

    const board = boardDummy

    this.props.history.push(`/board`, {board})
  }

//TODO: is getting the ref of BoardTemplate and do: this.boardTemplate.updateTemplates() the best solution??

  render() {// TODO: Adjacent JSX elements must be wrapped  in an enclosing tag??? Why must there be a div?
    return (
      <div>
        <h1 className="ui header">Create a board</h1>
        <Form>
          <Form.Field>
            <label>Title</label>
            <Input 
              name="title"
              onChange={this.onTitleChange}
            />   
          </Form.Field>
          <Form.Field>
            <label>Description</label>
            <TextArea onChange={this.onDescriptionChange} placeholder='Give more details about this board (optional)' />
          </Form.Field>
        </Form>
        
        <BoardTemplate 
          ref={boardTemplate => this.boardTemplate = boardTemplate} 
          templates={this.templates}/>

        <Button content='Create' primary style={{marginTop: 10}} onClick={this.submitCreateBoardReq.bind(this)}/>
      </div>
    )
  }
}
