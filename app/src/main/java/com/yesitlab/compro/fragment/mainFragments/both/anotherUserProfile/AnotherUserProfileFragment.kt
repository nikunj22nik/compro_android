package com.yesitlab.compro.fragment.mainFragments.both.anotherUserProfile

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import com.example.network.apiModel.HomeResponse
import com.yesitlab.compro.OnItemClickListener
import com.yesitlab.compro.OnItemClickListener1
import com.yesitlab.compro.R
import com.yesitlab.compro.adapter.CertificatePreviewAdapter
import com.yesitlab.compro.adapter.EducationPreviewAdapter
import com.yesitlab.compro.adapter.ExperiencePreviewAdapter
import com.yesitlab.compro.adapter.PortfolioAdapter
import com.yesitlab.compro.adapter.SkillPreviewAdapter
import com.yesitlab.compro.databinding.FragmentAnotherUserProfileBinding
import com.yesitlab.compro.databinding.LayoutLocationBinding
import com.yesitlab.compro.model.CertificatePreviewModel
import com.yesitlab.compro.model.EducationPreviewModel
import com.yesitlab.compro.model.ExperiencePreviewModel
import com.yesitlab.compro.model.PortfolioModel
import com.yesitlab.compro.setResizableText


class AnotherUserProfileFragment : Fragment(), OnClickListener, OnItemClickListener1,
    OnItemClickListener {
    lateinit var binding: FragmentAnotherUserProfileBinding

    private var includedLocationView: LayoutLocationBinding? = null
    private var experiencePreviewAdapter: ExperiencePreviewAdapter? = null
    private var experiencePreviewList: MutableList<ExperiencePreviewModel> = mutableListOf()
    private var educationPreviewAdapter: EducationPreviewAdapter? = null
    private var educationPreviewList: MutableList<EducationPreviewModel> = mutableListOf()

    private var skillPreviewAdapter: SkillPreviewAdapter? = null
    private var certificatePreviewAdapter: CertificatePreviewAdapter? = null
    private var certificatePreviewList: MutableList<CertificatePreviewModel> = mutableListOf()
    private lateinit var portfolioAdapter: PortfolioAdapter
    private var image_status = ""
    private var image_status1 = ""
    private val portfolioList: MutableList<PortfolioModel> = mutableListOf()

     var homeResponse : HomeResponse? = null

    private var skillList: MutableList<String> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

             homeResponse = arguments?.getParcelable<HomeResponse>("homeResponse")

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAnotherUserProfileBinding.inflate(
            LayoutInflater.from(requireContext()),
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.llExperience.setOnClickListener(this)
        binding.llEducation.setOnClickListener(this)
        binding.llLocation.setOnClickListener(this)
        binding.llResume.setOnClickListener(this)
        binding.llPortfolio.setOnClickListener(this)
        binding.llReview.setOnClickListener(this)
        includedLocationView = binding.includedLocationLayout

        // Then, find the views inside the included layout
        includedLocationView?.imageLocationEdit?.visibility = View.GONE

        initialize()
        adaptersInitialize()
//        var text =
//            "Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical Latin literature from 45 BC.Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical Latin literature from 45 BCContrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical Latin literature from 45 BC....."
//
//        binding.textDescription.setResizableText(text, 2, true)

    }

    private fun initialize() {
        // Skill



        homeResponse.let {
            binding.textName.text = it?.firstName + " " + it?.lastName
       binding.textProfilePhone.text = it?.mobile
            binding.textProfileEmail.text = it?.email
            binding.profileLocation.text = it?.country
            binding.ratingBar.rating = it?.rating_count?.toFloat() ?:0.0f
binding.textRatingNumber.text = it?.rating_count.toString()

            binding.textDescription.text = it?.title
            if (it != null) {
                binding.textDescription.setResizableText(it.title, 2, true)
            }

            val skillUser = it?.skills?.get(0)?.skill_user
            if (skillUser != null) {
                val stringArray = skillUser.split(",").toMutableList()

                skillPreviewAdapter = SkillPreviewAdapter(stringArray)
                binding.recyclerViewSkill.setAdapter(skillPreviewAdapter)


                skillPreviewAdapter?.notifyDataSetChanged()

            }
        }



      //  skillPreviewList()

    }

    @SuppressLint("NotifyDataSetChanged")
    private fun skillPreviewList() {
        skillList.add("c")
        skillList.add("c++")
        skillList.add("java")
        skillPreviewAdapter?.notifyDataSetChanged()
    }


    private fun showExperienceSection() {
        binding.viewExperience.visibility = View.VISIBLE
        binding.recyclerViewExperience.visibility = View.VISIBLE

        binding.viewEducation.visibility = View.GONE
        binding.viewLocation.visibility = View.GONE
        binding.viewResume.visibility = View.GONE
        binding.viewPortfolio.visibility = View.GONE
        binding.clResume.visibility = View.GONE
        binding.viewReview.visibility = View.GONE
        binding.clReview.visibility = View.GONE
        binding.recyclerViewEducation.visibility = View.GONE
        binding.recyclerViewCertificate.visibility = View.GONE
        binding.clPortfolio.visibility = View.GONE
        includedLocationView?.clLocation?.visibility = View.GONE
    }

    private fun showEducationSection() {
        binding.viewEducation.visibility = View.VISIBLE
        binding.recyclerViewEducation.visibility = View.VISIBLE

        binding.viewExperience.visibility = View.GONE
        binding.recyclerViewExperience.visibility = View.GONE
        binding.viewLocation.visibility = View.GONE
        binding.viewResume.visibility = View.GONE
        binding.viewPortfolio.visibility = View.GONE
        binding.clResume.visibility = View.GONE
        binding.viewReview.visibility = View.GONE
        binding.clReview.visibility = View.GONE
        binding.recyclerViewCertificate.visibility = View.GONE
        binding.clPortfolio.visibility = View.GONE
        includedLocationView?.clLocation?.visibility = View.GONE
    }

    private fun showLocationSection() {
        binding.viewLocation.visibility = View.VISIBLE
        includedLocationView?.clLocation?.visibility = View.VISIBLE

        binding.viewEducation.visibility = View.GONE
        binding.viewExperience.visibility = View.GONE
        binding.recyclerViewExperience.visibility = View.GONE
        binding.viewResume.visibility = View.GONE
        binding.viewPortfolio.visibility = View.GONE
        binding.clResume.visibility = View.GONE
        binding.viewReview.visibility = View.GONE
        binding.clReview.visibility = View.GONE
        binding.recyclerViewCertificate.visibility = View.GONE
        binding.clPortfolio.visibility = View.GONE
        binding.recyclerViewEducation.visibility = View.GONE
    }

    private fun showPortfolioSection() {
        binding.viewPortfolio.visibility = View.VISIBLE
        binding.clPortfolio.visibility = View.VISIBLE


        binding.viewEducation.visibility = View.GONE
        binding.viewExperience.visibility = View.GONE
        binding.recyclerViewExperience.visibility = View.GONE
        binding.viewResume.visibility = View.GONE
        binding.clResume.visibility = View.GONE
        binding.viewLocation.visibility = View.GONE
        binding.viewReview.visibility = View.GONE
        binding.clReview.visibility = View.GONE
        binding.recyclerViewCertificate.visibility = View.GONE
        includedLocationView?.clLocation?.visibility = View.GONE
        binding.recyclerViewEducation.visibility = View.GONE
    }

    private fun showResumeSection() {
        binding.viewResume.visibility = View.VISIBLE
        binding.clResume.visibility = View.VISIBLE
        binding.viewPortfolio.visibility = View.GONE
        binding.clPortfolio.visibility = View.GONE


        binding.viewEducation.visibility = View.GONE
        binding.viewExperience.visibility = View.GONE
        binding.recyclerViewExperience.visibility = View.GONE

        binding.viewLocation.visibility = View.GONE
        binding.viewReview.visibility = View.GONE
        binding.clReview.visibility = View.GONE
        binding.recyclerViewCertificate.visibility = View.GONE
        includedLocationView?.clLocation?.visibility = View.GONE
        binding.recyclerViewEducation.visibility = View.GONE
    }


    private fun showReviewSection() {


        binding.viewReview.visibility = View.VISIBLE
        binding.clReview.visibility = View.VISIBLE


        binding.viewResume.visibility = View.GONE
        binding.clResume.visibility = View.GONE
        binding.viewPortfolio.visibility = View.GONE
        binding.clPortfolio.visibility = View.GONE


        binding.viewEducation.visibility = View.GONE
        binding.viewExperience.visibility = View.GONE
        binding.recyclerViewExperience.visibility = View.GONE

        binding.viewLocation.visibility = View.GONE
        binding.recyclerViewCertificate.visibility = View.GONE
        includedLocationView?.clLocation?.visibility = View.GONE
        binding.recyclerViewEducation.visibility = View.GONE
    }

    private fun adaptersInitialize() {
        // experience







        experiencePreviewAdapter =
            ExperiencePreviewAdapter(requireContext(), mutableListOf(), this, false)
        binding.recyclerViewExperience.setAdapter(experiencePreviewAdapter)
        experiencePreview()
        experiencePreviewAdapter!!.updateItem(experiencePreviewList)




        // education
        educationPreviewAdapter =
            EducationPreviewAdapter(requireContext(), mutableListOf(), this, false)
        binding.recyclerViewEducation.setAdapter(educationPreviewAdapter)
       // educationPreview()
        homeResponse?.let { educationPreviewAdapter!!.updateItem(it.education) }



//        educationPreviewAdapter =
//            EducationPreviewAdapter(requireContext(), mutableListOf(), this, false)
//        binding.recyclerViewEducation.setAdapter(educationPreviewAdapter)
//        educationPreview()
//        educationPreviewAdapter!!.updateItem(educationPreviewList)

        // Skill

//        skillPreviewAdapter = SkillPreviewAdapter(skillList)
//        binding.recyclerViewSkill.setAdapter(skillPreviewAdapter)
//        skillPreviewList()


        certificatePreviewAdapter =
            CertificatePreviewAdapter(requireContext(), mutableListOf(), this, false)
        binding.recyclerViewCertificate.setAdapter(certificatePreviewAdapter)
        certificateList()
        certificatePreviewAdapter?.updateItem(certificatePreviewList)



        portfolioAdapter = PortfolioAdapter(requireActivity(), mutableListOf(), this, false)
        binding.recyclerViewPortfolio.adapter = portfolioAdapter
        loadPortfolioItems()
        portfolioAdapter.updateItem(portfolioList)


    }

    private fun loadPortfolioItems() {
        repeat(6) {
            portfolioList.add(PortfolioModel(R.drawable.image_portfolio, "Graphic Design"))
        }
    }

    private fun certificateList() {
        repeat(5) {
            certificatePreviewList.add(
                CertificatePreviewModel(
                    "oxford software institute",
                    "123456789110",
                    "nnnddd",
                    "11-11-2011",
                    "12-12-2012",
                    R.drawable.certificate_image
                )
            )
        }
    }

    private fun educationPreview() {
        repeat(6) {
            educationPreviewList.add(
                EducationPreviewModel(
                    "Savitri Public School",
                    "MCA",
                    "IT",
                    "India",
                    "2024-08-22",
                    "2022-09-22"
                )
            )
        }
    }

    private fun experiencePreview() {
        repeat(6) {
            experiencePreviewList.add(
                ExperiencePreviewModel(
                    "head field solution pvt",
                    "Noida",
                    "front end developer",
                    "2019-05-29",
                    "2024-08-22",
                    "India"
                )
            )
        }
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.llExperience -> {
                showExperienceSection()
            }

            R.id.llEducation -> {
                showEducationSection()
            }

            R.id.llLocation -> {
                showLocationSection()
            }

            R.id.llResume -> {
                showResumeSection()
            }

            R.id.llPortfolio -> {
                showPortfolioSection()
            }

            R.id.llReview -> {
                showReviewSection()
            }
        }
    }

    override fun itemClick(position: Int, source: String) {
        TODO("Not yet implemented")
    }

    override fun itemClick(position: Int) {
        TODO("Not yet implemented")
    }

}