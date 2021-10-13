import React, { useState, Component } from 'react';
import axios from 'axios';

import { makeStyles } from '@material-ui/core/styles';
import Card from '@material-ui/core/Card';
import CardActionArea from '@material-ui/core/CardActionArea';
import CardActions from '@material-ui/core/CardActions';
import CardContent from '@material-ui/core/CardContent';
import CardMedia from '@material-ui/core/CardMedia';
import Button from '@material-ui/core/Button';
import Typography from '@material-ui/core/Typography';
import TextField from '@material-ui/core/TextField';
import Grid from '@material-ui/core/Grid';

import EditIcon from '@material-ui/icons/Edit';

//file-upload-drop-zone
import Dropzone from 'react-dropzone';
import '../../assets/css/dataupload.css'

const useStyles = makeStyles({
  root: {
    maxWidth: 400,
    margin: 0
  },
});

export default function MediaCard(props) {
  const [classLabelName, setClassLabelName] = useState('');
  const classes = useStyles();

  const classChange = (e) => {
    props.onChange(e.target.name, e.target.value);
    setClassLabelName(e.target.value);
  };

  return (
    <Card className={classes.root}>
      <CardActionArea>
        <CardContent>
          <Grid container justifyContent="center" alignItems="center">
            <Grid item xs={9}>
              <Typography gutterBottom variant="h5" component="h2">
                <TextField
                  id="outlined-name"
                  name={props.dataClass}
                  label={props.dataClass}
                  onChange={classChange}
                  variant="outlined"
                />
              </Typography>
            </Grid>
              <Typography variant="body2" color="textSecondary" component="p">
                <Basic
                projectName={props.projectName}
                classLabelName={classLabelName}
                isUploaded = {
                        function(_isData, _projectID){
                          setNextBtnDisabled(false)
                          setProjectId(_projectID)
                          console.log(_projectID)
                }.bind(this)}/>
              </Typography>
          </Grid>
        </CardContent>
      </CardActionArea>
    </Card>
  );
}


{/* dropzone basic */ }
class Basic extends Component {
  constructor() {
    super();
    this.onDrop = (files) => {
      this.setState({ files })
    };
    this.state = {
      files: []
    };
  }

  imgUpload(e) {
    e.preventDefault();
    var upload_this = this;
    const api = axios.create({
      baseURL: 'http://localhost:8080'
    })
    const frm = new FormData();
    var photoFile = document.getElementById("file");
    if (photoFile.files[0] === undefined) {
      alert("데이터가 없습니다. 데이터를 입력해주세요.")
      return;
    }
    var idx = 0;
    for (idx = 0; idx < photoFile.files.length; idx++) {
      frm.append("files", photoFile.files[idx]);
    }
    console.log("projectName : "+ upload_this.props.projectName)
    frm.append("projectName", upload_this.props.projectName);
    frm.append("className", upload_this.props.classLabelName);

    api.post('/data/upload', frm, {
      headers: {
        'Content-Type': 'multipart/form-data'
      }
    }).then(function (response) {
      alert("데이터가 업로드되었습니다.")
      /*upload_this.props.isUploaded(
        true, response.data
      );*/
      console.log(response);
    }).catch(function (error) {
      alert("데이터 형식이 잘못되었습니다. 이미지 형식을 넣어주세요.");
      console.log(error);
    });

  }

  render() {
    const files = this.state.files.map(file => (
      <li key={file.name}>
        {file.name} - {file.size} bytes
      </li>
    ));


    return (
      <Dropzone onDrop={this.onDrop}>
        {({ getRootProps, getInputProps }) => (
          <section >
            <div {...getRootProps({ className: 'dropzone' })}>
              <input id="file" {...getInputProps()} />
              <p><strong>이곳을 클릭하여 데이터들을 선택하세요</strong></p>
            </div>
            <aside>
              <h5>Files</h5>
              <ul>{files}</ul>
            </aside>
            <center><Button style={{ backgroundColor: "#04ABC1", color: "white" }} onClick={this.imgUpload.bind(this)} variant="contained">업로드하기</Button></center>
          </section>
        )}
      </Dropzone>
    );
  }
}