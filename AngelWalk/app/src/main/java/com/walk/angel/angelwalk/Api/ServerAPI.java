package com.walk.angel.angelwalk.Api;

import com.walk.angel.angelwalk.Data.CommonData;
import com.walk.angel.angelwalk.Data.LoginData;
import com.walk.angel.angelwalk.Data.SightList;
import com.walk.angel.angelwalk.Data.SignupData;
import com.walk.angel.angelwalk.Data.board.BoardData;
import com.walk.angel.angelwalk.Data.board.BoardDetailData;
import com.walk.angel.angelwalk.Data.board.BoardList;
import com.walk.angel.angelwalk.Data.SignupData;
import com.walk.angel.angelwalk.Data.board.CommentList;
import com.walk.angel.angelwalk.Data.board.LikeData;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ServerAPI {
    @FormUrlEncoded
    @POST("user/sign-in")
    Call<LoginData> sendLoginData(@Field("id") String userId, @Field("password") String userPassword);

    @FormUrlEncoded
    @POST("user/sign-up")
    Call<SignupData> sendSignupData(@Field("id") String userId,
                                    @Field("nickName") String userNickname,
                                    @Field("password") String userPassword,
                                    @Field("passwordCheck") String userPasswordCheck);

    ////////////////////////////////////////////////////
    // 게시판
    @GET("board")
    Call<BoardList> getBoardList(@Query("startIndex") int pageNumber);

    @FormUrlEncoded
    @POST("board/create")
    Call<CommonData> createBoard(@Field("title") String title, @Field("content") String content);

    @GET("board/read/{boardSeq}")
    Call<BoardDetailData> getBoard(@Path("boardSeq") int boardIndex);

    @FormUrlEncoded
    @POST("board/update/{boardSeq}")
    Call<CommonData> updateBoard(@Path("boardSeq") int boardIndex, @Field("title") String title, @Field("content") String content);

    @FormUrlEncoded
    @POST("board/delete/{boardSeq}")
    Call<CommonData> deleteBoard(@Path("boardSeq") int boardIndex);

    @GET("comment/{boardSeq}")
    Call<CommentList> getComment(@Path("boardSeq") int boardIndex, @Query("startIndex") int pageNumber);

    @FormUrlEncoded
    @POST("comment/create/{boardSeq}")
    Call<CommonData> createComment(@Path("boardSeq") int boardIndex, @Field("content") String content);

    @FormUrlEncoded
    @POST("comment/update/{commentSeq}")
    Call<CommonData> updateComment(@Field("content") String content);

    @FormUrlEncoded
    @POST("comment/delete/{commentSeq}")
    Call<CommonData> deleteComment(@Path("commentSeq") String commentIndex);


    ////////////////////////////////////////////////////
    // 관광지
    @GET("sight")
    Call<SightList> getSightList();

    @POST("like/{boardSeq}")
    Call<LikeData> updateLike(@Path("boardSeq") int boardIndex);

    @FormUrlEncoded
    @POST("user/update/nick-name")
    Call<CommonData> sendNicknameUpdate(@Field("nickName") String userNickname);

    @FormUrlEncoded
    @POST("user/update/password")
    Call<CommonData> sendPasswordUpdate(@Field("currentPassword") String currentPassword,
                                        @Field("newPassword") String newPassword);
}
