import React, { useState, Component, Fragment } from 'react';
import { refreshToken } from 'components/Token.js';
import CustomFileInputCard from "components/CustomInput/CustomFileInputCard.js";

import Grid from '@material-ui/core/Grid';
import Paper from '@material-ui/core/Paper';

export default function DataCheck(props) {

    return (
        <>
            <Grid container spacing={2}>
                <Grid item xs={12}>
                    <Paper className={fixedHeightPaper}>
                        <div>
                            <h2><strong>데이터 업로드 결과</strong></h2>
                        </div>
                    </Paper>
                </Grid>
            </Grid>
        </>
    );
}