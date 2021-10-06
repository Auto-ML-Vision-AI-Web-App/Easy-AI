import React, {useState, Component} from 'react';
import axios from 'axios';
import { Link } from "react-router-dom";

import { makeStyles } from '@material-ui/core/styles';
import Grid from '@material-ui/core/Grid';
import Paper from '@material-ui/core/Paper';
import Button from '@material-ui/core/Button';
import clsx from 'clsx';

//file-upload-drop-zone
import Dropzone from 'react-dropzone';
import '../../assets/css/dataupload.css'

const useStyles = makeStyles((theme) => ({
  stepButton: {
    border: "red",
    backgroundColor: "#eee6c4",
    color: "black",
    fontSize: 30,
    margin : '10px',
    "&:hover,&:focus": {
      backgroundColor: "#333333",
      color: "#fff",
      boxShadow:
        "0 14px 26px -12px rgba(51, 51, 51, 0.42), 0 4px 23px 0px rgba(0, 0, 0, 0.12), 0 8px 10px -5px rgba(51, 51, 51, 0.2)",
    },
  },
  container: {
    paddingTop: theme.spacing(4),
    paddingBottom: theme.spacing(4),
  },
  paper: {
    padding: theme.spacing(2),
    display: 'flex',
    overflow: 'auto',
    flexDirection: 'column',
  },
  fixedHeight: {
    height: 500,
  },
}));

export default function DataUpload(props) {
  const classes = useStyles();
  const fixedHeightPaper = clsx(classes.paper, classes.fixedHeight);
  const [prevBtnDisabled, setPrevBtnDisabled] = useState(true)
  const [nextBtnDisabled, setNextBtnDisabled] = useState(true)
  const [projectId, setProjectId] = useState(null)
  
  return (
        <>
          <Grid container spacing={2}>
            <Grid item xs={12}>
                    <Paper className={fixedHeightPaper}>
                    <div>
                        {props.location.state==undefined ?
                        <div>
                        <h3>현재 데이터가 업로드 되지 않았습니다.</h3>
                        <h4>데이터 업로드하기를 눌러 데이터를 입력해주세요.</h4>
                        </div>
                        :
                        <div>
                        {/*showData(props.location.state.projectId)*/}
                        <h4>프로젝트 Type : {props.location.state.selectedType}</h4>
                        <p>데이터 수 : XX</p>
                        </div>
                      }</div>
                      <h4>데이터 업로드하기</h4>
                      <Basic isUploaded = {
                        function(_isData, _projectID){
                          setNextBtnDisabled(false)
                          setProjectId(_projectID)
                          console.log(_projectID)
                      }.bind(this)}/>
                    </Paper>
                    <center>
                    <Button component={Link} to="/admin"
                    disabled={prevBtnDisabled}
                    className={classes.stepButton}>
                    이전
                    </Button>
                    <Link to={{
                    pathname : "/admin/data-checking",
                    state : {
                        projectId : projectId,
                    }
                    }}
                    ><Button 
                    disabled={nextBtnDisabled}
                    className={classes.stepButton}>
                    다음
                    </Button>
                    </Link>
                    </center>
            </Grid>
          </Grid>
        </>
  );
}

{/* dropzone basic */}
class Basic extends Component {
  constructor() {
    super();
    this.onDrop = (files) => {
      this.setState({files})
    };
    this.state = {
      files: []
    };
  }
  
  imgUpload(e){
    e.preventDefault();
    var upload_this = this;
    const api = axios.create({
      baseURL: 'http://localhost:8080'
    })
    const frm = new FormData();
    var photoFile = document.getElementById("file");
    if(photoFile.files[0]===undefined){
      alert("데이터가 없습니다. 데이터를 입력해주세요.")
      return;
    }
    var idx=0;
    for (idx=0; idx < photoFile.files.length; idx++) {
      frm.append("files", photoFile.files[idx]);
    }
    
    api.post('/upload', frm, {
      headers: {
        'Content-Type': 'multipart/form-data'
      }
      }).then(function (response) {
        alert("데이터가 업로드되었습니다.")
        upload_this.props.isUploaded(
          true, response.data
        );
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
        {({getRootProps, getInputProps}) => (
          <section >
            <div {...getRootProps({className: 'dropzone'})}>
              <input id="file" {...getInputProps()} />
              <h3><strong>파일들을 끌어다 놓거나, 이곳을 클릭하여 자신의 데이터들을 선택하세요</strong></h3>
            </div>
            <aside>
              <h5>Files</h5>
              <ul>{files}</ul>
            </aside>
            <center><Button style={{backgroundColor: "#04ABC1", color:"white"}} onClick={this.imgUpload.bind(this)} variant="contained">업로드하기</Button></center>
          </section>
        )}
      </Dropzone>
    );
  }
}