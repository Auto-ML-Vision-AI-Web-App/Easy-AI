import React, {useState, Component} from 'react';
import axios from 'axios';
import { makeStyles } from '@material-ui/core/styles';
import Grid from '@material-ui/core/Grid';
import Paper from '@material-ui/core/Paper';
import Button from '@material-ui/core/Button';
import IconButton from '@material-ui/core/IconButton';
import SkipNextIcon from '@material-ui/icons/SkipNext';
import SkipPreviousIcon from '@material-ui/icons/SkipPrevious';
import clsx from 'clsx';

//file-upload-drop-zone
import Dropzone from 'react-dropzone';
import '../../assets/css/dataupload.css'

const useStyles = makeStyles((theme) => ({
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

export default function DataUpload() {
  const classes = useStyles();
  const fixedHeightPaper = clsx(classes.paper, classes.fixedHeight);
  const [btnDisabled, setBtnDisabled] = useState(true)
  const [isData, setData] = useState(null)
  
  return (
        <>
          <Grid container spacing={2}>
            <Grid item xs={12}>
                    <Paper className={fixedHeightPaper}>
                      <h2><strong>데이터 업로드하기</strong></h2>
                      <Basic />
                    </Paper>
                    <center>
                    <IconButton disabled={btnDisabled} color="secondary" aria-label="go to next step">
                    <SkipNextIcon style={{ fontSize: 80 }} />
                    </IconButton>
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
              <h3><strong>파일들을 끌어다 놓거나, 클릭하여 자신의 데이터들을 선택하세요</strong></h3>
            </div>
            <aside>
              <h4>Files</h4>
              <ul>{files}</ul>
            </aside>
            <center><Button onClick={this.imgUpload.bind(this)} variant="contained">업로드하기</Button></center>
          </section>
        )}
      </Dropzone>
    );
  }
}