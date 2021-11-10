import React from 'react';
import { makeStyles } from '@material-ui/core/styles';
import clsx from 'clsx';
import axios from 'axios';
import Grid from '@material-ui/core/Grid';
import Paper from '@material-ui/core/Paper';
import Button from '@material-ui/core/Button';
import LinearProgress from '@material-ui/core/LinearProgress';

import { Redirect, useHistory } from "react-router-dom";


const useStyles = makeStyles((theme) => ({
    cardRoot: {
        maxWidth: 400,
        margin: theme.spacing(2),
    },
    root: {
        display: 'flex',
    },
    container: {
        paddingTop: theme.spacing(4),
        paddingBottom: theme.spacing(4),
    },
    paper: {
        padding: theme.spacing(2),
        display: 'flex',
        flexDirection: 'column',
    },
    fixedHeight: {
        height: 500,
    },
    card_big: {
    },
    card_box: {
        display: 'flex',
        flexDirection: 'row',
        padding: theme.spacing(2),
    },
    card: {
        margin: theme.spacing(2),
    }
}));

export default function AIChoose(props) {
    const classes = useStyles();
    const fixedHeightPaper = clsx(classes.paper, classes.fixedHeight);

    const setAIProject_AIChoose = (_type, _projectName) =>{
        props.setAIType(_type);
        props.setProjectName(_projectName);
    }
    return (
        <>
            {/* for test */}
            <Grid container spacing={3}>
                <Grid alignItems="center" item xs={12}>
                    <Paper className={classes.paper}>
                        <h2><strong>AI 선택하기</strong></h2>
                        <p>설명을 읽고 만들고자 하는 AI를 선택하세요(선택 1)</p>
                        <Grid
                            container
                            spacing={0}
                            direction="column"
                            alignItems="center"
                            justify="center"
                        >
                            <div className={classes.card_box}>
                                <Grid item xs={6}>
                                    <ImgMediaCard
                                        setAIProject={setAIProject_AIChoose}
                                        card_value="image-classification"
                                        card_title="이미지 분류하기"
                                        card_content="이미지 분류하기란 사진이 무슨 사진인지 구별해주는 작업입니다. 단순 예로는, 사진처럼 이 사진이 의자인지 아닌지를 구분해줍니다."
                                        card_img="https://machinelearningmastery.com/wp-content/uploads/2018/12/Example-of-Image-Classification-with-Localization-of-Multiple-Chairs-from-VOC-2012.jpg"
                                        detail_desc=""
                                    ></ImgMediaCard>
                                </Grid>
                                <Grid item xs={6}>
                                    <ImgMediaCard
                                        setAIProject={setAIProject_AIChoose}
                                        card_value="object-detection"
                                        card_title="물체 탐지하기"
                                        card_content="물체 탐지하기란 사진에 여러 물체가 있을 때 각각의 물체 주변에 네모 상자를 두르고 물체명을 붙이는 작업입니다.    "
                                        card_img="https://machinelearningmastery.com/wp-content/uploads/2018/12/Example-of-Object-Detection-with-Faster-R-CNN-on-the-MS-COCO-Dataset.png"
                                    ></ImgMediaCard>
                                </Grid>
                            </div>
                            <div className={classes.card_box}>
                                <Grid item xs={6}>
                                    <ImgMediaCard
                                        setAIProject={setAIProject_AIChoose}
                                        card_value="colorization"
                                        card_title="이미지 색입히기"
                                        card_content="이미지 색 입히기란 흑백 이미지를 칼라 이미지로 바꿔주는 작업입니다."
                                        card_img="https://machinelearningmastery.com/wp-content/uploads/2018/12/Examples-of-Photo-Colorization.png"
                                    ></ImgMediaCard>
                                </Grid>
                                <Grid item xs={6}>
                                    <ImgMediaCard
                                        setAIProject={setAIProject_AIChoose}
                                        card_value="style-transfer"
                                        card_title="스타일 트랜스퍼"
                                        card_content="스타일 트랜스퍼란 사진의 스타일을 다른 사진에 적용하여 새 이미지를 만드는 작업입니다."
                                        card_img="https://machinelearningmastery.com/wp-content/uploads/2018/12/Example-of-Neural-Style-Transfer-from-Famous-Artworks-to-a-Photograph.png"
                                    ></ImgMediaCard>
                                </Grid>
                            </div>
                        </Grid>
                    </Paper>
                </Grid>
            </Grid>
        </>
    );
}

import Card from '@material-ui/core/Card';
import CardActionArea from '@material-ui/core/CardActionArea';
import CardActions from '@material-ui/core/CardActions';
import CardContent from '@material-ui/core/CardContent';
import CardMedia from '@material-ui/core/CardMedia';
import Typography from '@material-ui/core/Typography';

function ImgMediaCard(props) {
    const classes = useStyles();
    const history = useHistory();

    function aiMaking(card_value) {
        const randomProjectName = Math.random().toString(36).substr(2,11);
        console.log("AI model choosing");
        history.push({
            pathname: '/admin/data-uploading',
        })
        props.setAIProject(card_value, randomProjectName);
    }

    return (
        <Card className={classes.cardRoot}>
            <CardActionArea card_value={props.card_value} onClick={() => { aiMaking(props.card_value); }}>
                <CardMedia
                    component="img"
                    alt="test"
                    height="200"
                    image={props.card_img}
                    title="test"
                />
                <CardContent>
                    <Typography gutterBottom variant="h5" component="h2">
                        {props.card_title}
                    </Typography>
                    <Typography variant="body2" color="textSecondary" component="p">
                        {props.card_content}
                    </Typography>
                </CardContent>
            </CardActionArea>
            <CardActions>
            </CardActions>
        </Card>
    );
}
