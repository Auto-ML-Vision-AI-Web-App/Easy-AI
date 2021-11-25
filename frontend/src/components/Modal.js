import React, { useState, useEffect } from 'react';
import axios from 'axios';

import { withStyles } from '@material-ui/core/styles';
import Button from '@material-ui/core/Button';
import Dialog from '@material-ui/core/Dialog';
import MuiDialogTitle from '@material-ui/core/DialogTitle';
import MuiDialogContent from '@material-ui/core/DialogContent';
import MuiDialogActions from '@material-ui/core/DialogActions';
import IconButton from '@material-ui/core/IconButton';
import CloseIcon from '@material-ui/icons/Close';
import Typography from '@material-ui/core/Typography';
import Chip from '@material-ui/core/Chip';
import Grid from '@material-ui/core/Grid';

import * as DataTable from "components/Table.js";

const styles = (theme) => ({
    root: {
        margin: 0,
        padding: theme.spacing(2),
    },
    closeButton: {
        position: 'absolute',
        right: theme.spacing(1),
        top: theme.spacing(1),
        color: theme.palette.grey[500],
    },
});

const DialogTitle = withStyles(styles)((props) => {
    const { children, classes, onClose, ...other } = props;
    return (
        <MuiDialogTitle disableTypography className={classes.root} {...other}>
            <Typography variant="h6">{children}</Typography>
            {onClose ? (
                <IconButton aria-label="close" className={classes.closeButton} onClick={onClose}>
                    <CloseIcon />
                </IconButton>
            ) : null}
        </MuiDialogTitle>
    );
});

const DialogContent = withStyles((theme) => ({
    root: {
        padding: theme.spacing(2),
    },
}))(MuiDialogContent);

const DialogActions = withStyles((theme) => ({
    root: {
        margin: 0,
        padding: theme.spacing(1),
    },
}))(MuiDialogActions);

export default function CustomizedDialogs(props) {
    const [open, setOpen] = React.useState(props.open);
    const [modelList, setModelList] = React.useState();
    const [tags, setTags] = React.useState(props.tags);

    const handleClose = () => {
        props.handleClickClose();
        setOpen(false);
    };

    const changeModelList = (modelArr) => {
        console.log(modelArr)
        setModelList(modelArr);
    }

    return (
        <div>
            <Dialog maxWidth='md' fullWidth='true' onClose={handleClose} aria-labelledby="customized-dialog-title" open={props.open}>
                <DialogTitle id="customized-dialog-title" onClose={handleClose}>
                    사전 학습 모델 List
                    <p>* 모델은 하나만 선택해주세요</p>
                </DialogTitle>
                <DialogContent dividers>
                    <Grid container spacing={3}>
                    {props.tags.map((tag, idx) =>(
                        <ModelBtn setModelList={changeModelList} label={tag}></ModelBtn>
                    ))}
                    </Grid>
                    <br></br>
                    <br></br>
                    <Grid container spacing={3}>
                        <DataTable.CheckTable rows={modelList}></DataTable.CheckTable>
                    </Grid>
                </DialogContent>
                <DialogActions>
                    <Button autoFocus onClick={handleClose} color="primary">
                        모델 결정하기
                    </Button>
                </DialogActions>
            </Dialog>
        </div>
    );
}

function ModelBtn(props) {
    const [modelBtn, setModelBtn] = React.useState(false);

    const chipClick = (e) => {
        const flag = !modelBtn;
        var rows =[];
        var rows2 = [];
        console.log(flag);
        
        const api = axios.create({
            baseURL: 'http://localhost:8080'
        })
        api.get(`/tags/${e.target.innerHTML}`, {
            headers: {
                'Authorization': "Bearer " + localStorage.getItem('refresh-token'),
            }
        }).then(function (response) {
            const models = response.data;
            console.log(models)
            models.map((model, idx) =>{
                var labelStr = '';
                model.classes.map((label)=>{labelStr = label+", "});

                var obj = {};
                obj.id = idx;
                obj.model = model.projectName;
                obj.owner = model.username;
                obj.accuracy = model.accuracy;
                obj.loss = model.loss;
                obj.label = labelStr;
                Object.preventExtensions(obj);

                rows = rows.concat(obj);
                })
            console.log(rows);
            //props.setModelList(rows);
            flag ? props.setModelList(rows) : props.setModelList(rows2);
        }).catch(function (error) {
            console.log(error);
        });

        /*const rows = [
            { id: 1, model: 'Model 1', owner: 'h01010', accuracy: 0.8, loss: 0.1, label: 'label1, label2' },
            { id: 2, model: 'Model 2', owner: 'dbdorud', accuracy: 0.7, loss: 0.2, label: 'label1, label2' },
            { id: 3, model: 'Model 3', owner: 'donghun', accuracy: 0.99, loss: 0.13, label: 'label1, label2' },
        ];*/
        setModelBtn(!modelBtn);
    };

    return (
        <Grid item>
            <Chip clickable onClick={chipClick} color={modelBtn ? "primary" : ""} label={props.label} />
        </Grid>
    );
}
