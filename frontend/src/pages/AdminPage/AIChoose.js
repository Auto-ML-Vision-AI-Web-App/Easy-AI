import React, { useState, useEffect } from 'react';
import axios from 'axios';

import { makeStyles } from '@material-ui/core/styles';
import clsx from 'clsx';
import Grid from '@material-ui/core/Grid';
import Paper from '@material-ui/core/Paper';
import Button from '@material-ui/core/Button';

import CustomizedDialogs from "components/Modal.js";

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
    const [open, setOpen] = React.useState(false);
    const [tags, setTags] = React.useState([]);

    const handleClickOpen = () => {
        setOpen(true);
        const api = axios.create({
            baseURL: 'http://localhost:8080'
        })
        api.get('/tags', {
            headers: {
                'Authorization': "Bearer " + localStorage.getItem('refresh-token'),
            }
        }).then(function (response) {
            console.log(response.data);
            response.data.map((tag, idx) =>{
                console.log(tag)
            })
            setTags(response.data);
        }).catch(function (error) {
            console.log(error);
        });
    };

    const handleClickClose = () =>{
        setOpen(false);
    }

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
                        <h2><strong>AI ????????????</strong></h2>
                        <p>????????? ?????? ???????????? ?????? AI??? ???????????????(?????? 1)</p>
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
                                        card_title="????????? ????????????"
                                        card_content="????????? ??????????????? ????????? ?????? ???????????? ??????????????? ???????????????. ?????? ?????????, ???????????? ??? ????????? ???????????? ???????????? ??????????????????."
                                        card_img="https://machinelearningmastery.com/wp-content/uploads/2018/12/Example-of-Image-Classification-with-Localization-of-Multiple-Chairs-from-VOC-2012.jpg"
                                        detail_desc=""
                                        setOpenDial={handleClickOpen}
                                    ></ImgMediaCard>
                                </Grid>
                                <Grid item xs={6}>
                                    <ImgMediaCard
                                        setAIProject={setAIProject_AIChoose}
                                        card_value="object-detection"
                                        card_title="?????? ????????????"
                                        card_content="?????? ??????????????? ????????? ?????? ????????? ?????? ??? ????????? ?????? ????????? ?????? ????????? ????????? ???????????? ????????? ???????????????.    "
                                        card_img="https://machinelearningmastery.com/wp-content/uploads/2018/12/Example-of-Object-Detection-with-Faster-R-CNN-on-the-MS-COCO-Dataset.png"
                                        setOpenDial={handleClickOpen}
                                    ></ImgMediaCard>
                                </Grid>
                            </div>
                            <div className={classes.card_box}>
                                <Grid item xs={6}>
                                    <ImgMediaCard
                                        setAIProject={setAIProject_AIChoose}
                                        card_value="colorization"
                                        card_title="????????? ????????????"
                                        card_content="????????? ??? ???????????? ?????? ???????????? ?????? ???????????? ???????????? ???????????????."
                                        card_img="https://machinelearningmastery.com/wp-content/uploads/2018/12/Examples-of-Photo-Colorization.png"
                                        setOpenDial={handleClickOpen}
                                    ></ImgMediaCard>
                                </Grid>
                                <Grid item xs={6}>
                                    <ImgMediaCard
                                        setAIProject={setAIProject_AIChoose}
                                        card_value="style-transfer"
                                        card_title="????????? ????????????"
                                        card_content="????????? ??????????????? ????????? ???????????? ?????? ????????? ???????????? ??? ???????????? ????????? ???????????????."
                                        card_img="https://machinelearningmastery.com/wp-content/uploads/2018/12/Example-of-Neural-Style-Transfer-from-Famous-Artworks-to-a-Photograph.png"
                                        setOpenDial={handleClickOpen}
                                    ></ImgMediaCard>
                                </Grid>
                            </div>
                        </Grid>
                    </Paper>
                </Grid>
            </Grid>

            <CustomizedDialogs tags={tags} handleClickClose={handleClickClose} open={open}></CustomizedDialogs>
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

    const _handleClickOpen = () => {
        props.setOpenDial(true);
    };

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
                <Button variant="contained" size="small" onClick={_handleClickOpen}>?????? ?????? ?????? ????????????</Button>
            </CardActions>
        </Card>
    );
}
