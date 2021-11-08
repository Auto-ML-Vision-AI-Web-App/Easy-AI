import React, { useState, Component } from 'react';
import axios from 'axios';
import {setCookie, getCookie, removeCookie} from 'components/Cookie.js';
import {refreshToken} from 'components/Token.js';

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
import LinearProgress from '@material-ui/core/LinearProgress';


//file-upload-drop-zone
import Dropzone from 'react-dropzone';
import '../../assets/css/dataupload.css'

const useStyles = makeStyles({
  root: {
    maxWidth: 400,
    margin: 0
  },
});

export function TrainDataUpload(props) {
  const [classLabelName, setClassLabelName] = useState('');
  const classes = useStyles();

  const classChange = (e) => {
    props.onChange(e.target.name, e.target.value);
    setClassLabelName(e.target.value);
  };

  const setDate = (_className, _path, _size) => {
    props.setNewDate(_className, _path, _size);
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
                <DropdownInput
                category="train"
                fileId={props.id}
                projectName={props.projectName}
                classLabelName={classLabelName}
                isUploaded = {setDate}/>
              </Typography>
          </Grid>
        </CardContent>
      </CardActionArea>
    </Card>
  );
}

export function TestDataUpload(props) {
  const [classLabelName, setClassLabelName] = useState('');
  const classes = useStyles();

  const setDate = (_className, _path, _size) => {
    props.setNewDate(_className, _path, _size);
  };

  return (
    <Card className={classes.root}>
      <CardActionArea>
        <CardContent>
          <Grid container justifyContent="center" alignItems="center">
              <Typography variant="body2" color="textSecondary" component="p">
                <DropdownInput
                category="test"
                fileId={props.id}
                projectName={props.projectName}
                isUploaded = {setDate}/>
              </Typography>
          </Grid>
        </CardContent>
      </CardActionArea>
    </Card>
  );
}


{/* dropzone basic */ }
class DropdownInput extends Component {
  constructor() {
    super();
    this.onDrop = (files) => {
      //console.log(files)
      this.setState({ files })
    };
    this.state = {
      files: [],
      startUpload : false,
      loadingStatus : false,
    };
  }

  imgUpload(e) {
    this.setState({startUpload: true, loadingStatus: true});
    var upload_this = this;
    e.preventDefault();
    const api = axios.create({
      baseURL: 'http://localhost:8080'
    })
    const frm = new FormData();
    var photoFile = document.getElementById(upload_this.props.fileId);
    if (photoFile.files[0] === undefined) {
      alert("데이터가 없습니다. 데이터를 입력해주세요.")
      return;
    }
    var idx = 0;
    for (idx = 0; idx < photoFile.files.length; idx++) {
      console.log(idx);
      frm.append("files", photoFile.files[idx]);
    }
    frm.append("projectName", upload_this.props.projectName);
    if(upload_this.props.category==="train") frm.append("className", upload_this.props.classLabelName);
    frm.append("category", upload_this.props.category);

    console.log(upload_this.props.projectName);

    api.post('/data/upload', frm, {
      headers: {
        'Authorization':"Bearer "+getCookie('access-token'),
        'Content-Type': 'multipart/form-data'
      }
    }).then(function (res) {
      alert("데이터가 업로드되었습니다.");
      upload_this.setState({loadingStatus: false});
      const data = res.data;
      console.log(data);
      upload_this.props.isUploaded(data.className, "", data.numOfSuccess);
    }).catch(function (error) {
      if(error.response) {
        if(error.response.status == '403'){
          var flag = refreshToken();
          if(flag===1) imgUpload();
        }
      }
    });

  }

  render() {
    const files = this.state.files.map(file => (
      <li key={file.name}>
        {file.name} - {file.size} bytes
      </li>
    ));
    return (
      <>
      <Dropzone onDrop={this.onDrop}>
        {({ getRootProps, getInputProps }) => (
          <section >
            <div {...getRootProps({ className: 'dropzone' })}>
              <input id={this.props.fileId} {...getInputProps()} />
              <p><strong>이곳을 클릭하여 데이터들을 선택하세요</strong></p>
            </div>
            <aside>
              <h5>Files</h5>
              <ul>{files}</ul>
            </aside>
            <center><Button style={{ backgroundColor: "#04ABC1", color: "white" }} onClick={this.imgUpload.bind(this)} variant="contained">업로드하기</Button></center>
            <br></br>
            {this.state.startUpload?
            <LoadingProgressOrResult loading={this.state.loadingStatus}></LoadingProgressOrResult>
              : <></>
            }
          </section>
        )}
        
      </Dropzone>
      </>
    );
  }
}

function LoadingProgressOrResult(props) {
  return (
      <>
      {console.log("loading : " + props.loading)}
      {props.loading ?
                    <LinearProgress id="loadingProgress"/>
                    : <p>데이터 업로드를 완료하였습니다.</p>}
          
      </>
  );
}
